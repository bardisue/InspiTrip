import mysql.connector
from mysql.connector import Error


def get_codes_from_address(address):
    # Database configuration
    config = {
        'host': 'localhost',
        'user': 'ssafy',
        'password': 'ssafy',
        'database': 'mukbang'
    }

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
                return {"sido_code": sido_result[0], "gugun_code": gugun_result[0]}
            else:
                return "No matching records found."
    except Error as e:
        return f"Error: {str(e)}"
    finally:
        if connection.is_connected():
            cursor.close()
            connection.close()


# Example usage:
address = "서울 성동구 왕십리광장로 6 1층 스시도쿠 왕십리본점"
codes = get_codes_from_address(address)
print(codes)
