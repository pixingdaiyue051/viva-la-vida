1. vm option:-Djava.library.path=doc/opencv
2. mvn install:install-file \
        -Dfile=opencv-4.5.4.jar \
        -DgroupId=com.tequeno \
        -DartifactId=opencv \
        -Dversion=4.5.4 \
        -Dpackaging=jar