# Fragments

 compatible with Android 4.0+.

## Gradle

 
```groovy 
 implementation 'com.chaek.android:fragments:1.0.2'
```

## Usage
```java
            Fragments.with(this)
                     .addToBackStack()
                     .putString("title", "item1")
                     .fragment(Fragment1.class/*new Fragment1()*/)
                     .into(R.id.test);

         
       Fragment f  =  Fragments.get(MainFragment.class)
                             .putString("title", "title")
                             .get();

```


## License
    Copyright 2017 shallcheek

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
