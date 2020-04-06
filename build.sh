#!/bin/bash
function find_project_name {
	project="eee"
	for((i=3;i<=10;i++));  
	do   
		prev="a"
		tmp=`pwd | cut -d '/' -f $i`
		if [ -n $tmp ]; then
			prev=$tmp
		fi
		echo $prev
	done  
}


#mvn clean install -Dmaven.test.skip=true
curdir=`pwd`

project=`find_project_name`
echo "project is $project"
rm -rf dist
mkdir dist
appclass="org.springframework.boot.loader.JarLauncher"
srcfiles="$curdir/target/jexplorer-0.0.1-SNAPSHOT.jar"
outdir="$curdir/dist/"
cmd="javapackager -deploy -native image -appclass $appclass -srcfiles $srcfiles -outdir $outdir -outfile $project -name $project"
`$cmd`