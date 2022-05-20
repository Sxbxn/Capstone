import os
import shutil

dir = '/Users/kimtaekang/Desktop/study/capstone/AI/'
result_output = dir + 'run/result/'

if os.path.isdir(result_output + '/label'):
    shutil.rmtree(result_output + '/label')