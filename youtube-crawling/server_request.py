import json
import requests
# ('', '', '', '03197', '02-2273-8203', '', '0', '', '', '', '')
attr_data = {
    'contentId': '0',
    'contentTypeId': '39',
    'title': '남이분식',
    'address': '서울 종로구 종로40가길 3',
    'image': 'https://mblogthumb-phinf.pstatic.net/MjAyMzEyMjdfNDAg/MDAxNzAzNjI4NTE2NDM2.sGPf9PmlOrRtPt4cJgjgDfr7ZN5qxRlNkJQJqxYpNFcg.Sb2D5Ejt3R0F0hcx0anCOCCg5dysLBDsEQrqUyex7zIg.PNG.jeong_928/C30B519D-D83C-4959-A373-A4093F7C25FB.png?type=w800',
    'sidoCode': '1',
    'gunguCode': '23',
    'latitude': '37.570199',
    'longitude': '127.006535',
    'overview': '',
}

visit_data ={
    'mid': 'ssafy',
    'ino': '',
    'ano': '',
    'url': '',
    'iName': '',
    'iImg': '',
}

influence_id = "@tzuyang6145"
attr_url = 'http://localhost/attr/insert'
influencer_url = f"http://localhost/influencer/findname/{influence_id}"
response = requests.post(attr_url, json=attr_data)
attr_content_id = response.text  # 응답 내용 출력

response = requests.get(influencer_url)
influence_info = json.loads(response.text)

visit_data['ino'] = influence_info['no']
visit_data['ano'] = attr_content_id
visit_data['url'] = ""

