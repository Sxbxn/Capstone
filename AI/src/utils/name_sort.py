import os

dir = '/home/dh/Project/Capstone/data/K562_Pointing_data/'

files = os.listdir(dir)

for file in files:
    extension = file[-4:]
    f = file.split('_')
    try:
        result = f[2]
        os.rename(dir+file, dir+f[0]+'_'+result+extension)
    except:
        os.rename(dir+file, dir+f[0]+extension)