#!/bin/sh

# requires jq library to parse json ,you can install usin brew install jq command if you have hombrew on u r mac


# colored echo function to print text in yellow
function cEcho(){
local exp=$1;
local color=$2;
if ! [[ $color =~ '^[0-9]$' ]] ; then
case $(echo $color | tr '[:upper:]' '[:lower:]') in
black) color=0 ;;
red) color=1 ;;
green) color=2 ;;
yellow) color=3 ;;
blue) color=4 ;;
magenta) color=5 ;;
cyan) color=6 ;;
white|*) color=7 ;; # white or invalid color
esac
fi
tput setaf $color;
echo $exp;
tput sgr0;
}



# get comment
comment="$1"

if [ "$comment" == "" ]; then
comment="version 0.1"
cEcho "no comment specified to deploy, using default : $comment" yellow
fi

#get directories
fileName="deployment.json"

cssPath=($(jq -r '.css' $fileName))

htmlPath=($(jq -r '.html' $fileName))

jsPath=($(jq -r '.js' $fileName))

imagesPath=($(jq -r '.images' $fileName))


# validating directories

if [ -d "$cssPath" ]; then
 isCssPath=true
fi

if [ -d "$htmlPath" ]; then
 isHtmlPath=true
fi

if [ -d "$jsPath" ]; then
 isJsPath=true
fi

if [ -d "$imagesPath" ]; then
 isImagesPath=true
fi



git checkout master


cEcho "In master branch , copying deployment files from development" yellow

if [ $isCssPath ]; then
  git checkout development -- "$cssPath"
fi

if [ $isHtmlPath ]; then
  git checkout development -- "$htmlPath"
fi

if [ $isJsPath ]; then
  git checkout development -- "$jsPath/*.js"
fi

if [ $isImagesPath ]; then
  git checkout development -- "$imagesPath"
fi

git checkout development -- index.html
git checkout development -- README.md


cEcho " pushing changes to master" yellow

git commit -a -m "$comment"
git push origin master

cEcho " successfully deployed changes to gh-pages" yellow

git checkout development
