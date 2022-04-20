import pandas as pd
import json
import glob


input_csv = '/home/dh/Project/Capstone/data/patch_train/csv/'
input_img = '/home/dh/Project/Capstone/data/patch_train/img/'
output_json = '/home/dh/Project/Capstone/examples/'

csvs = glob.glob(input_csv+'*.*')
imgs = glob.glob(input_img+'*.*')
csvs.sort()
imgs.sort()

category = [ {'id': 0, 'name': 'dead', 'supercategory': 'cell'}, {'id': 1, 'name': 'live', 'supercategory': 'cell'}]
image = []
annotation = []
count = 0

for idx, csv in enumerate(csvs):
    
    img_name = imgs[idx]
    img = {'id': idx, 'file_name': img_name, 'height': 306, 'width': 408, 'date_captured': None}
    image.append(img)

    df = pd.read_csv(csv)

    for idx2, row in df.iterrows():
        val = {'id': count, 'image_id': idx, 'category_id': int(row[2]), 'bbox':[int(row[3]),int(row[4]),int(row[5]),int(row[6])]}
        count += 1
        annotation.append(val)

output = { 'info': {}, 'licenses': [], 'categories': category, 'images': image, 'annotations': annotation}
textJSON = json.dumps(output, indent=4)

with open(output_json+'cell_count_train.json', 'w') as f:
    f.write(textJSON)
    print('complete saving '+output_json+'cell_count_train.json')

