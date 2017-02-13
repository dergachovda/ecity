echo "delete /app & /img"
rm -r ./src/main/resources/static/app
rm -r ./src/main/resources/static/img

cd ../web
git pull origin master

echo "copy web project to /resources/static/app"
cp -r ./dist ../ecity/src/main/resources/static/app
cd ../ecity/src/main/resources/static/

echo "move img to /resources/static/"
mv app/img img

echo "copy favicon.ico to ./app"
cp ./favicon.ico ./app


