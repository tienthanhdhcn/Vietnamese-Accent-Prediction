# Vietnamese Accent Prediction
## This project is to automatically assign accents to non-accented Vietnamese text using n-gram languagle model with markov chain
- In this code, I use uni-gram and bi-grams. However, you can easily extend to tri-grams.

### Examples <br/>
- "Anh yeu em" --> "Anh yêu em" (I love you) <br/>
- "Toi dang di du lich o ha long" --> "Tôi đang đi du lịch ở hạ long" (I am visting Halong) <br/>

### API <br/>
#### Using the provided n-grams data

```java

boolean loadingSmallDatasets = false // It will load the large n-gram datasets in the datasets folder
AccentPredictor ap = new AccentPredictor(loadingSmallDatasets)
String str = "Toi thich di du lich Ha Noi"
String predictedStr = ap.predictAccents(str)

```

#### Using your own data

```java
AccentPredictor ap = new AccentPredictor("_1GramFile", "_2GramsFile", "vocabFile")
String str = "Toi thich di du lich Ha Noi"
String predictedStr = ap.predictAccents(str)

```

