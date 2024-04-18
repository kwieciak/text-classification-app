# Classification of articles from Reuters-21578 dataset using KNN algorithm

## About the project
This project is a text analysis tool with interactive graphical interface that reads and processes articles. It uses various extractors to gather information from the articles, such as the most used city, country, etc. It also calculates the sum of words beginning with a capital letter, the sum of unique words, the sum of common words, etc. The project then uses the k-nearest neighbors (kNN) algorithm for classification 

![gui before classification](https://github.com/kwieciak/text-classification-app/project1/src/main/resources/org/example/project1/img/gui1.jpg)
![gui after classification](https://github.com/kwieciak/text-classification-app/project1/src/main/resources/org/example/project1/img/gui2.jpg)


## Features
*Reads and processes articles from a specified directory
*Uses various extractors to gather information from the articles
*Normalizes the features extracted from the articles
*Uses the k-nearest neighbors (kNN) algorithm for classification
*Calculates and prints classification stats
*GUI

### Built with
*Java
*JavaFX
*Maven

## Roadmap
- [ ] Add more extractors
- [ ] Add multithreading support for knn
