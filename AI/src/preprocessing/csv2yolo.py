import pandas as pd
import glob


input_csv = '/home/dh/Project/Capstone/data/patch_val/csv/'
output_yolo = '/home/dh/Project/Capstone/data/patch_val/data/'
input_img =  '/home/dh/Project/Capstone/data/patch_val/data/'
output_txt = '/home/dh/Project/Capstone/data/patch_val/'
x_size = 408
y_size = 306

csvs = glob.glob(input_csv+'*.*')

for csv_name in csvs:
    csv = pd.read_csv(csv_name)
    name = csv_name.split('/')[-1][:-4]+'.txt'
    f = open(output_yolo+name, 'w')

    for line in csv.itertuples():
        print(line)
        classes = line[3]
        x = (line[4]+20) / x_size
        y = (line[5]+20) / y_size
        w = 40 / x_size
        h = 40 / y_size
        #f.write('{} {} {} {} {}\n'.format(classes, x, y, w, h))

    print('save '+output_yolo+name)
    f.close()

imgs = glob.glob(input_img+'*.jpg')
f = open(output_txt+'val.txt', 'w')

#for img_name in imgs:
    #f.write(img_name+'\n')

print('save'+output_txt+'val.txt')
f.close()