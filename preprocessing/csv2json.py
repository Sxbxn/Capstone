import pandas as pd
import json
import glob


input_csv = '/home/dh/Project/Capstone/data/patch_train/csv/'
input_img = '/home/dh/Project/Capstone/data/patch_train/img/'
output_json = '/home/dh/Project/Capstone/data/patch_train/annotated/'

csvs = glob.glob(input_csv+'*.*')
imgs = glob.glob(input_img+'*.*')
csvs.sort()
imgs.sort()

for idx, csv in enumerate(csvs):
    
    img = imgs[idx]
    df = pd.read_csv(csv)
    shapes = []

    for idx2, row in df.iterrows():
        val = {'label': 'live' if row[2]==1 else 'dead', 'points':[[int(row[3]),int(row[4]) ],[int(row[5]),int(row[6])]], 'group_id': None, 'shape_type': 'rectangle', 'flags':  {}}
        shapes.append(val)

    result = {'version': '4.0.0', 'flags':{}, 'shapes': shapes, 'imagePath': img,   'imageData': None, "imageHeight": 306, "imageWidth": 408 }

    textJSON = json.dumps(result, indent=4)

    with open(output_json+csv.split('/')[-1][:-4]+'.json', 'w') as f:
        f.write(textJSON)
        print('save '+output_json+csv.split('/')[-1][:-4]+'.json')
