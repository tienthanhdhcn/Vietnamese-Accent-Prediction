# Vietnamese Accent Prediction
## A very simple/fast/accurate accent prediction for non-accented Vietnamese text using n-gram languagle model with markov chain

### Performances
#### All the tests were done on my Macbook, 2.5 GHz Intel Core i7, 16 GB Ram
- Speed: **350** sentences per second ~ **4000** words/syllables per second on 
- Accuracy: **95.13%** on *test.txt* provided in *datasets* folder

```java
AccuracyCalculator ac = new AccuracyCalculator(); 
System.out.println("Accuracy:" + ac.getAccuracy("datasets/test.txt") +"%");
```

### Examples
- *Anh yeu em* --> *Anh yêu em* (I love you) 

- *Toi dang di du lich o ha long* --> *Tôi đang đi du lịch ở hạ long* (I am visting Halong) 


### API
#### Using the provided n-grams data

```java
AccentPredictor ap = new AccentPredictor();
String str = "Toi thich di du lich Ha Noi";
String predictedStr = ap.predictAccents(str);

```

#### Using your own n-gram data

```java
AccentPredictor ap = new AccentPredictor("_Your1GramFile", "_Your2GramsFile");
String str = "Toi thich di du lich Ha Noi";
String predictedStr = ap.predictAccents(str);

```

- To create your own n-gram data, you can use the following API:

```java
String dataFolderPath = "path_to_your_data"; //The folder contains your text data
int numberOfProcessingFiles = -1; //The max number of files you plan to process (-1 means using all the data)
boolean toLowercase = true; // it is set to "true", the n-grams will be converted to lowercase
String _1GramFileOut =  "datasets/news1gram";
String _2GramsFileOut =  "datasets/news2grams";
new NGramer(dataFolderPath).statisticNGrams(numberOfProcessingFiles, toLowercase, _1GramFileOut, _2GramsFileOut);

```