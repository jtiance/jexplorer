#!/bin/bash
project="jxplorer"
echo "$project"
curdir=`pwd`

mvn clean install -Dmaven.test.skip=true
mkdir -p target/config
cp target/classes/*.properties target/config/
cp start.sh target/

rm -rf dist
mkdir dist
appclass="org.springframework.boot.loader.JarLauncher"
srcfiles="$curdir/target/jexplorer-0.0.1-SNAPSHOT.jar"
outdir="$curdir/dist/"
javapackager -deploy -native image -appclass $appclass -srcfiles $srcfiles -outdir $outdir -outfile $project -name $project
mkdir -p "dist/bundles/$project/app/config"
cp target/classes/*.properties "dist/bundles/$project/app/config/"