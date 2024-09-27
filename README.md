## Изучение работы с Custom View и Canvas в Android (Kotlin)

Этот проект предназначен для изучения и демонстрации работы с `Custom View` и `Canvas` в Android с использованием языка Kotlin. <br/>
В проекте представлены три фрагмента, каждый из которых содержит разные пользовательские представления (`Custom View`).<br/>

### Описание

Проект включает в себя три фрагмента, каждый из которых демонстрирует различные техники рисования на `Canvas`:<br/>

- **Таб 1**: [`ShapeDrawingFragmentView`] `ShapeDrawingView`<br/>
Сделан по заданию:<br/>
  Что оно должно уметь
  - Должна быть возможность выставить дефолтный цвет через кастомный атрибут. Если он не выставлен, то дефолтный цвет должен быть зеленый
  - Должно уметь принимать в коде массив цветов, в hex, или Int формате.
  - Должно уметь рисовать круг, квадрат, квадрат с закругленными краями.
  Требования:<br/>
  - View должно отображаться на весь экран.
  - По нажатию на любую точку на экране в этой точке рисуется рандомная фигура из указанных выше, и рандомного цвета, из переданного массива.
  - Если массив цветов пуст, то цвет становится по умолчанию дефолтный.
  - Вверху View должно отображаться текстом текущее количество фигур на экране.
  - При достижении 10 штук - поле очищается, счетчик обнуляется. И выводится toast с надписью "Game over"
  Дополнительно:<br/>
  - Размер фигуры должен быть тоже рандомный, в определенном промежутке. Например от 20 до 50 dp.
  - Язык реализации Kotlin и стандартные инструменты Android SDK 

<details><summary>Рандомные цвета</summary>

https://github.com/user-attachments/assets/2b76ec8c-a6f4-4d91-98d9-30f2450bd7e9

</details>

<details><summary>Дефолтный цвет</summary>  

https://github.com/user-attachments/assets/926d6fc4-984e-4ba2-af7b-7a0fd176ff28

</details>
  
- **Таб 2**: [`LandscapeDrawableFragmentView`]
  - Использована `LandscapeDrawable` custom view из открытого доступа.<br/>
 [Источник](https://github.com/ZieIony/GuideToCustomViews/tree/master/landscapedrawable/src/main/java/com/github/zieiony/guide/landscapedrawable)<br/>

<details><summary>Гифка</summary>

  ![2_video](https://github.com/user-attachments/assets/1f2addfb-8cab-4490-a448-0715d18567d5)
    
</details>
  
- **Таб 3**: [`CircularAvatarsLineFragmentView`]
  - Написана своя custom view `AvatarsLineView` для отображения круглых картинок в линию до 4х штук, далее круг с указанием сколько ещё нужно было нарисовать.
  
<details><summary>Скриншот</summary>
  
  ![3](https://github.com/user-attachments/assets/80c8cc1c-2854-425d-8fcd-c0077ac0e143)

</details>
