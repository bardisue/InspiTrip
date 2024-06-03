import time
import os
import sys
import urllib.request
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
from selenium.common.exceptions import StaleElementReferenceException, NoSuchElementException, TimeoutException
from tqdm import tqdm
from openai import OpenAI
import json
from collections import Counter
import mysql.connector
from mysql.connector import Error
import datetime
import requests
from bs4 import BeautifulSoup

# Database configuration
config = {
    'host': 'localhost',
    'user': 'ssafy',
    'password': 'ssafy',
    'database': 'mukbang'
}

client = OpenAI(
    # This is the default and can be omitted
    # api_key='sk-proj-sREvm3SAVX1OhED6vEonT3BlbkFJr1II370dgYddqy9ft98o',
    api_key='sk-proj-Xd6G750GNpD0VL2DcjmkT3BlbkFJ2gYvg7yLTGT7Sf0DCQxN'
)

# WebDriver 설정 (Chrome 사용)
driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()))

attr_info = {}
attr_desc = {}

print_state = True


# influence를 매개변수로 받아 채널에 있는 모든 영상의 제목과 영상 링크를 가져옴
def get_influence_videos(influence):
    channel = 'https://www.youtube.com'

    # 원하는 웹 페이지 열기
    driver.get(f"{channel}/{influence}/videos")

    last_position = driver.execute_script("return window.pageYOffset;")

    while True:
        actions = driver.find_element(By.CSS_SELECTOR, 'body')
        actions.send_keys(Keys.END)
        time.sleep(1)

        current_position = driver.execute_script("return window.pageYOffset;")

        if current_position == last_position:
            break
        last_position = current_position

    # 스크롤 완료 후 요소 찾기
    elements = driver.find_elements(By.CSS_SELECTOR, '#video-title-link')

    with open(f"influence/{influence}.txt", "w", encoding='utf-8') as file:
        for element in tqdm(elements, desc="총 영상"):
            file.write(f"{element.get_attribute('title')} $|$ {element.get_attribute('href')}\n")


# 변수 초기화
def initialize_global():
    global attr_info
    global attr_desc
    attr_info = {
        'content_type_id': 39,
        'title': '',
        'addr1': '',
        'zipcode': '',
        'tel': '',
        'first_image': '',
        'readcount': 0,
        'sido_code': '',
        'gugun_code': '',
        'latitude': '',
        'longitude': '',
        'overview': '',
        'bookingUrl': '',
    }
    attr_desc = {
        'content_id': '',
        'overview': '',
    }

# 유튜브 영상 링크를 매개변수로 받아서 해당 영상의 스크립트를 읽어옴
def get_script(url):
    driver.get(url)
    time.sleep(3)
    more_button = driver.find_element(By.CSS_SELECTOR, '#expand')
    # 화면 중앙에 보이도록 스크롤
    driver.execute_script("arguments[0].scrollIntoView({block: 'center'});", more_button)
    time.sleep(1)  # 화면이 재정렬될 시간 제공
    more_button.click()
    time.sleep(1)
    # "스크립트 표시" 버튼 찾기
    driver.find_element(By.XPATH, '/html/body/ytd-app/div[1]/ytd-page-manager/ytd-watch-flexy/div[5]/div[1]/div/div[2]/ytd-watch-metadata/div/div[4]/div[1]/div/ytd-text-inline-expander/div[2]/ytd-structured-description-content-renderer/div/ytd-video-description-transcript-section-renderer/div[3]/div/ytd-button-renderer/yt-button-shape/button').click()
    time.sleep(1)
    script_container = driver.find_element(By.TAG_NAME, 'ytd-transcript-renderer')
    elements = script_container.find_elements(By.TAG_NAME, 'yt-formatted-string')
    text = '\n'.join(element.text for element in elements)
    # print(text)

    # json_string = '{ "address": "서울특별시 종로구 성균관로 41 1층", "title": "명륜진사갈비"}'
    if text != '':
        json_string = extract_store_details(text)
        if json_string != 'null':
            # print(json_string)
            data = json.loads(json_string)
            # print(data)

            query = f"{data['address']} {data['title']}"
            # print(f"{data['address']} {data['title']}")
            get_address(query)

            time.sleep(1)




# 영상의 스크립트를 GPT-3.5를 이용해서 JSON 형식의 도로명 주소와 가게 이름을 추출
def extract_store_details(text):
    prompt = f"아래의 영상의 스크립트에서 도로명 주소와 가게 이름을 {{ address : 추출한 도로명 주소, title: 추출한 가게 이름}} JSON 형식으로 작성해주세요.\n{text}\n"
    completion = client.chat.completions.create(model="gpt-3.5-turbo", messages=[{"role": "user", "content": prompt}])
    return completion.choices[0].message.content


# 도로명 주소와 가게 이름을 합쳐 네이버 블로그 검색 API를 이용해서 10개의 블로그 링크를 추출
# 추출한 블로그 링크에 들어가 네이버 맵 API에 적용한 JSON 정보를 가져옴
def get_address(text):
    data_list = []
    client_id = "W7BNIP_PLwywT8bdWutH"
    client_secret = "tl301N72in"
    encText = urllib.parse.quote(text)
    url = "https://openapi.naver.com/v1/search/blog?query=" + encText  # JSON 결과
    request = urllib.request.Request(url)
    request.add_header("X-Naver-Client-Id", client_id)
    request.add_header("X-Naver-Client-Secret", client_secret)
    response = urllib.request.urlopen(request)
    rescode = response.getcode()
    if (rescode == 200):
        response_body = response.read()
        data = json.loads(response_body.decode('utf-8'))
        items = data['items']

        for item in items:
            # print(item['link'])
            driver.get(item['link'])
            time.sleep(1)
            try:
                iframe = driver.find_element(By.TAG_NAME, 'iframe')
                driver.switch_to.frame(iframe)
                # html_content = driver.execute_script("return document.documentElement.outerHTML;")
                # print(html_content)
                map_info = driver.find_element(By.CSS_SELECTOR, 'div.se-module.se-module-map-text > a')
                # print(map_info.get_attribute('data-linkdata'))
                data = json.loads(map_info.get_attribute('data-linkdata'))
                data_list.append(f"{data['placeId']},{data['name']},{data['address']},{data['latitude']},{data['longitude']},{data['tel']},{data['bookingUrl']}")

            except NoSuchElementException:
                print()
                # print("get_address >> 해당 요소를 찾을 수 없습니다.")

        # Counter 객체를 사용하여 데이터 카운팅
        counter = Counter(data_list)

        # 가장 많이 나타나는 데이터 찾기
        most_common_data = counter.most_common(1)[0][0]
        # print(most_common_data)
        split_common = most_common_data.split(',')

        placeId = split_common[0]
        attr_info['title'] = split_common[1]
        attr_info['latitude'] = split_common[3]
        attr_info['longitude'] = split_common[4]
        attr_info['tel'] = split_common[5]
        attr_info['bookingUrl'] = split_common[6]
        get_map_info(placeId)
    else:
        print("get_address >> Error Code:" + rescode)


# # 네이버 지도에 placeId를 입력하여 해당 가게에 대한 정보를 가져옵니다.
# def get_map_info2(placeId):
#     url = f"https://pcmap.place.naver.com/restaurant/1810003724/home{placeId}"
#     driver.get(url)
#     time.sleep(5)
#     try:
#         iframe = driver.find_element(By.ID, 'entryIframe')
#         driver.switch_to.frame(iframe)
#         driver.find_element(By.CSS_SELECTOR, '.PkgBl').click()
#         time.sleep(2)
#         elements = driver.find_elements(By.CSS_SELECTOR, '.vV_z_ > .Y31Sf > .nQ7Lh')
#         road_address = elements[0].text.replace('도로명', '').replace('복사', '')
#         time.sleep(2)
#         # html_content = driver.execute_script("return document.documentElement.outerHTML;")
#         # print(html_content)
#         time.sleep(1)
#         print("도로명 주소: "+road_address)
#         attr_info['addr1'] = road_address
#         print("지번: " + elements[1].text.replace('지번', '').replace('복사', ''))
#         export_zipcode = elements[2].text.replace('우편번호', '').replace('우', '').replace('복사', '').replace('\n', '')
#         print("우편번호: " + export_zipcode)
#         attr_info['zipcode'] = export_zipcode
#
#         get_google_image()
#         time.sleep(1000)
#     except NoSuchElementException:
#         print("해당 요소를 찾을 수 없습니다.")
#     finally:
#         codes = get_codes_from_address(attr_info['addr1'])
#         print(codes)
#         print(attr_info)


def get_map_info(placeId):
    home_url = f"https://pcmap.place.naver.com/restaurant/{placeId}/home"
    information_url = f"https://pcmap.place.naver.com/restaurant/{placeId}/information"
    try:
        driver.get(home_url)
        time.sleep(1)
        road_address = driver.find_element(By.CSS_SELECTOR,
                                           '#app-root > div > div > div > div:nth-child(5) > div > div:nth-child(2) > div.place_section_content > div > div.O8qbU.tQY7D > div > a > span.LDgIH').text
        attr_info['addr1'] = road_address
        # print("도로명 주소: " + road_address)

        driver.get(information_url)
        time.sleep(1)
        overview = driver.find_element(By.CSS_SELECTOR,
                                       '#app-root > div > div > div > div:nth-child(6) > div > div.place_section.no_margin.Od79H > div > div > div.Ve1Rp > div').text
        attr_info['overview'] = overview
        # print("소개: " + overview)

    except NoSuchElementException:
        print("get_map_info >> 해당 요소를 찾을 수 없습니다.")
    finally:
        get_google_image()
        codes = get_codes_from_address(attr_info['addr1'])
        # print(codes)
        # print(attr_info)


# 해당 가게에 대한 정보를 검색하여 가장 첫번째에 나오는 이미지를 가져옵니다.
def get_google_image():
    try:
        driver.get(f"https://www.google.co.kr/imghp?hl=ko&ogbl")
        time.sleep(1)
        driver.find_element(By.CSS_SELECTOR, '#APjFqb').send_keys(f"{attr_info['addr1']} {attr_info['title']}")
        time.sleep(0.5)
        driver.find_element(By.CSS_SELECTOR, '.Tg7LZd').click()
        time.sleep(1)
        image_container = driver.find_element(By.CSS_SELECTOR, '#rcnt')
        img_a = driver.find_element(By.CSS_SELECTOR,
                                    '#rso > div > div > div.wH6SXe.u32vCb > div > div > div:nth-child(1) > div.czzyk.XOEbc > h3 > a').click()
        time.sleep(2)
        first_image = driver.find_element(By.CSS_SELECTOR,
                                          '#Sva75c > div.A8mJGd.NDuZHe.OGftbe-N7Eqid-H9tDt > div.LrPjRb > div.AQyBn > div.tvh9oe.BIB1wf > c-wiz > div > div > div > div > div.v6bUne > div.p7sI2.PUxBg > a > img.sFlh5c.pT0Scc.iPVvYb').get_attribute(
            'src')
        # print(first_image)
        attr_info['first_image'] = first_image
    except NoSuchElementException:
        print()
        # print("get_google_image >> 해당 요소를 찾을 수 없습니다.")


def get_codes_from_address(address):
    # Attempt to connect to the database
    try:
        connection = mysql.connector.connect(**config)
        if connection.is_connected():
            cursor = connection.cursor()

            # Parse the address for the district and city names
            address_parts = address.split()
            city = address_parts[0]
            district = address_parts[1]

            # Prepare and execute the SQL query
            query_sido = "SELECT sido_code FROM sido WHERE sido_name = %s"
            query_gugun = "SELECT gugun_code FROM gugun WHERE gugun_name = %s"

            cursor.execute(query_sido, (city,))
            sido_result = cursor.fetchone()

            cursor.execute(query_gugun, (district,))
            gugun_result = cursor.fetchone()

            # Check if the results exist
            if sido_result and gugun_result:
                attr_info['sido_code'] = sido_result[0]
                attr_info['gugun_code'] = gugun_result[0]
                return {"sido_code": sido_result[0], "gugun_code": gugun_result[0]}
            else:
                return "get_codes_from_address >> No matching records found."
    except Error as e:
        return f"get_codes_from_address >> Error: {str(e)}"
    finally:
        if connection.is_connected():
            cursor.close()
            connection.close()


def attr_insert_value(influence_id):
    attr_url = 'http://localhost/attr/insert'
    attr_data = {
        'contentId': '0',
        'contentTypeId': '39',
        'title': attr_info['title'],
        'address': attr_info['addr1'],
        'image': attr_info['first_image'],
        'sidoCode': attr_info['sido_code'],
        'gunguCode': attr_info['gugun_code'],
        'latitude': attr_info['latitude'],
        'longitude': attr_info['longitude'],
        'overview': attr_info['overview'],
    }
    response = requests.post(attr_url, json=attr_data) # insert 한 뒤 저장한 attr_content_id의 값을 반환
    attr_content_id = response.text  # 응답 내용 출력
    # print(attr_content_id)
    time.sleep(0.3)
    return attr_content_id


def influence_create_visited(influence_id, attr_content_id, url):
    visit_data = {
        'mid': 'ssafy',
        'ino': '',
        'ano': '',
        'url': '',
    }
    influencer_url = f"http://localhost/influencer/findname/{influence_id}"
    response = requests.get(influencer_url)
    influence_info = json.loads(response.text)
    visit_data['ino'] = influence_info['no']
    visit_data['ano'] = attr_content_id
    visit_data['url'] = url
    # print(visit_data)
    time.sleep(0.3)
    return visit_data


def influence_add_visited(data):
    visit_add = f"http://localhost/influencer/visit"
    response = requests.post(visit_add, json=data)
    time.sleep(0.3)
    return response.text


def read_text_file(influence_id, max):
    lines = []
    file_path = f"influence/{influence_id}.txt"
    with open(file_path, "r", encoding='utf-8') as file:
        # Read and process each line in the file
        for line in file:
            lines.append(line.strip())
    # 현재 날짜와 시간을 '년-월-일-시-분-초' 형식의 문자열로 가져옵니다.
    now = datetime.datetime.now().strftime("%Y%m%d%H%M%S")
    cnt = 0
    for line in tqdm(lines, desc="작업 현황"):
        if cnt < max:
            # Each line contains the title and URL separated by " | "
            title, url = line.strip().split(" $|$ ")
            try:
                initialize_global()
                get_script(url)
                if attr_info['addr1'] != "":
                    attr_content_id = attr_insert_value(influence_id)
                    if attr_content_id != "" and attr_content_id != "fail":
                        data = influence_create_visited(influence_id, attr_content_id, url)
                        result = influence_add_visited(data)

                    with open(f"log/filelists-{now}.txt", "a", encoding='utf-8') as outfile:
                        outfile.write(
                            f"{url} | '{attr_info['content_type_id']}', '{attr_info['title']}', '{attr_info['addr1']}'"
                            f", '{attr_info['zipcode']}', '{attr_info['tel']}', '{attr_info['first_image']}'"
                            f", '{attr_info['readcount']}', '{attr_info['sido_code']}', '{attr_info['gugun_code']}'"
                            f", '{attr_info['latitude']}', '{attr_info['longitude']}', '{attr_info['overview']}' | {result}\n")
            except StaleElementReferenceException:
                print(f"{url} >> Element has become stale. Skipping.")
            except NoSuchElementException:
                print("read_text_file >> Element no longer exists.")
            except TimeoutException:
                print("read_text_file >> Element loading timed out.")
            except Exception as e:
                print(e)
            finally:
                cnt += 1


def get_influencers_youtube_id():
    # 데이터베이스 연결 시도
    connection = None  # connection 초기화
    try:
        connection = mysql.connector.connect(**config)
        if connection.is_connected():
            cursor = connection.cursor()

            # SQL 쿼리 준비 및 실행
            query_influencers = "SELECT youtube_id FROM influencers"
            cursor.execute(query_influencers)

            # 모든 YouTube ID 가져오기
            youtube_ids = cursor.fetchall()  # fetchall을 사용하여 모든 결과를 가져옵니다.
            return youtube_ids  # 결과 리스트 반환
        else:
            return "No database connection could be established."
    except Error as e:
        return f"Error: {str(e)}"
    finally:
        # 연결이 열려있는 경우, 리소스 정리
        if connection.is_connected():
            cursor.close()
            connection.close()

if __name__ == "__main__":
    # Todo : DB에서 인플풀언서 데이터 리스트 받아서 처리
    influencers = get_influencers_youtube_id()
    # influence = '@tzuyang6145'
    for i in range(6, len(influencers)):
        influence = influencers[i]
        influence = influence[0].replace("'", "").replace("(", "").replace(")", "")
        print(influence)
        get_influence_videos(influence)
        read_text_file(influence, 100)

    # for influence in influencers:
    #     influence = influence[0].replace("'", "").replace("(", "").replace(")", "")
    #     print(influence)
    #     get_influence_videos(influence)
    #     read_text_file(influence, 200)

    # get_map_info2('1810003724')



