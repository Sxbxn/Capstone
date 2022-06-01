import os

from flask import Flask, request, jsonify, json
from werkzeug.utils import secure_filename
import py_eureka_client.eureka_client as eureka_client
import argparse
from auto import predict

ALLOWED_EXTENSIONS = set(['jpg'])  # 허용할 파일의 확장자들

image_path = "/app/server/run/data/original"
# image_path = "/Users/kimtaekang/Desktop/study/capstone/AI/run/data/original"
rest_port = 50000


parser = argparse.ArgumentParser()
parser.add_argument('host', nargs='?', type=str, default='localhost', help='ex) "localhost"')
args = parser.parse_args()

HOST_IP = args.host
print("**************" + HOST_IP + "******************")

# eureka_client.init(eureka_server="http://127.0.0.1:8761/eureka",
eureka_client.init(eureka_server="49.50.163.44" + ":8761/eureka",
                   app_name="capstone-detection-service",
                   instance_port=rest_port)
app = Flask(__name__)


@app.route('/info')
def hello_world():
    return 'Hello World!'


@app.route('/Detection', methods=['POST'])
def file_upload():
    file = request.files['file']

    filename = secure_filename(file.filename)
    print(filename)
    os.makedirs(image_path, exist_ok=True)
    file.save(os.path.join(image_path, filename))

    # auto.py 실행
    predict()

    with open('./ktk.json') as json_file:
        json_data = json.load(json_file)

    return jsonify(json_data).get_data("totalCell")


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=rest_port)
