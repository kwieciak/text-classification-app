# Classification of articles from Reuters-21578 dataset using KNN algorithm

## About the project
This project is a text analysis tool with interactive graphical interface that reads and processes articles. It uses various extractors to gather information from the articles, such as the most used city, country, etc. It also calculates the sum of words beginning with a capital letter, the sum of unique words, the sum of common words, etc. The project then uses the k-nearest neighbors (kNN) algorithm for classification 

![gui before classification](https://github.com/kwieciak/text-classification-app/blob/main/project1/src/main/resources/org/example/project1/img/gui.jpg)
![gui before classification](https://github.com/kwieciak/text-classification-app/blob/main/project1/src/main/resources/org/example/project1/img/gui2.jpg)


## Features
* Reads and processes articles from a specified directory
* Uses various extractors to gather information from the articles
* Normalizes the features extracted from the articles
* Uses the k-nearest neighbors (kNN) algorithm for classification
* Calculates and prints classification stats
* GUI

### Built with
* Java
* JavaFX
* Maven

## Roadmap
- [ ] Add more extractors
- [ ] Add multithreading support for knn

## Getting started
Make sure you have all the dependencies needed installed (Java 21 & JavaFX).
1. Clone the repository to your local machine: 
```sh
git clone https://github.com/kwieciak/text-classification-app.git
```
2. Open IDE and select "Open an existing project." Navigate to the cloned repository and select the "text-classification-app" directory.
3. Select a path:
```sh
src/main/java/org/example/project1/Application.java
```
4. Run 'Application.java'
