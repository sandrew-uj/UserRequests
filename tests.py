import requests


if __name__ == '__main__':
    res1 = requests.post('http://localhost:8090/api/auth/login', json={'username': 'ADMIN', 'password': 'a_pass'})
    # res1 = requests.post('http://localhost:8090/api/auth/register', json={'username': 'ADMIN1', 'password': 'a_pass'})
    # res1 = requests.post('http://localhost:8090/api/auth/login', json={'username': 'OPERATOR', 'password': 'o_pass'})
    # res1 = requests.post('http://localhost:8090/api/auth/login', json={'username': 'USER', 'password': 'u_pass'})
    headers = {
        'Authorization': res1.json()['tokenType'] + res1.json()['accessToken']
    }
    # res = requests.post(
    #     'http://localhost:8090/api/requests/user/create',
    #     headers=headers,
    #     json={'name': 'Chief kief', 'message': 'Fuck you', 'phone': '+79600076587'}
    # )
    # res = requests.post(
    #     'http://localhost:8090/api/requests/user/createDraft',
    #     headers=headers,
    #     json={'name': 'Chief kief', 'message': 'Fuck you', 'phone': '+79600076587'}
    # )
    # res = requests.get(
    #     'http://localhost:8090/api/requests/user/requests',
    #     headers=headers,
    #     params={'pageNo': 1, 'isAsc': False}
    # )
    # res = requests.post(
    #     'http://localhost:8090/api/requests/user/edit',
    #     headers=headers,
    #     json={'name': 'ABAABAABAB', 'message': 'HELLO BIATCH!', 'phone': '+79810232393', 'requestId': 16}
    # )
    # res = requests.get(
    #     'http://localhost:8090/api/requests/user/sendToOperator',
    #     headers=headers,
    #     params={'requestId': 11}
    # )
    # res = requests.get(
    #     'http://localhost:8090/api/requests/operator/getByUser/Andrew',
    #     headers=headers,
    #     # params={'requestId': 11}
    # )
    # res = requests.get(
    #     'http://localhost:8090/api/requests/admin/getByStatus',
    #     headers=headers,
    #     params={'status': 'DRAFT'}
    # )
    res = requests.get(
        'http://localhost:8090/api/users/admin/toOperator/5',
        headers=headers,
        # params={'status': 'DRAFT'}
    )
    print(res.status_code)
    # print(res.content)
    print(res.text)
    # print(res.json()['accessToken'])
