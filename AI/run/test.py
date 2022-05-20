import os

from flask import Flask, request
from werkzeug.utils import secure_filename
import py_eureka_client.eureka_client as eureka_client
from auto import predict

# UPLOAD_FOLDER = '/Users/kimtaekang/Desktop/study/capstone/AI/Development_log/Assets'  # 업로드된 파일이 저장되는 곳
ALLOWED_EXTENSIONS = set(['jpg'])  # 허용할 파일의 확장자들

image_path = "/app/server/run/data/original"
rest_port = 50000

eureka_client.init(eureka_server="3.36.183.94" + ":8761/eureka",
                   app_name="capstone-detection-service",
                   instance_port=rest_port)
app = Flask(__name__)


# app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


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

    return filename


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=rest_port)
