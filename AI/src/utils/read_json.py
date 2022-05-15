import json

with open('ObjectDetection/coco/annotations/instances_val2017.json', 'r') as f:
    json_data = json.load(f)
    textJSON = json.dumps(json_data, indent=4)

with open('cell_count_train.json', 'w') as f:
    f.write(textJSON)