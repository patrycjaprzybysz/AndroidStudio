package com.example.projekt

import android.content.Intent


import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        insertSampleData()

        val textViewHistory: TextView = findViewById(R.id.textViewHistory)
        textViewHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        val textViewTourism: TextView = findViewById(R.id.textViewTourism)
        textViewTourism.setOnClickListener {
            val intent = Intent(this, TourismActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerViewArticles)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val articles = listOf(
            ArticlePreview(
                1L,
                "Łukasz Kobus nowym sekretarzem i dyrektorem Urzędu Miasta Gdyni",
                R.drawable.artykul1
            ),
            ArticlePreview(
                2L,
                "10 ton w jeden weekend – kto śmieci na gdyńskich plażach?",
                R.drawable.artykul2
            )
        )

//        val adapter = ArticleAdapter(articles) { article ->
//            Toast.makeText(this, article.title, Toast.LENGTH_SHORT).show()
//
//        }
//        recyclerView.adapter = adapter
//    }

        val adapter = ArticleAdapter(articles) { article ->
            val fragment = ArticleDetailFragment()
            val bundle = Bundle()
            bundle.putLong("ARTICLE_ID", article.id)
            fragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        recyclerView.adapter = adapter

    }
    private fun insertSampleData() {
        val article1 = Article(
            id = 0,
            title = "Na początku było marzenie... Historia Gdyni w pigułce",
            date = "2023-05-31",
            elements = listOf(
                ArticleElement.Text("Odzyskanie przez Polskę w 1918 roku niepodległości, upragnionej po blisko 150 latach zaborów, nie przyniosło jej od razu długo wyczekiwanego dostępu do morza. Zaślubiny Polski z morzem odbyły się dopiero 10 lutego 1920 roku, po ratyfikacji Traktatu Wersalskiego. Na jego mocy przyznano Polsce skrawek wybrzeża. Bez portu."),
                ArticleElement.Text("Niecałe 3 miesiące po uroczystościach zaślubinach Polski z morzem w Pucku, rozpoczęły się poszukiwania najdogodniejszego miejsca pod budowę portu wojennego. W tym celu wiceadmirał Kazimierz Porębski – ówczesny dyrektor Departamentu Spraw Morskich Ministerstwa Spraw Wojskowych, oddelegował na Pomorze inż. Tadeusza Wendę. Okazał się on jedną z kluczowych postaci w historii Gdyni. Już w czerwcu 1920 roku Tadeusz Wenda złożył sprawozdanie z przeprowadzonej lustracji Wybrzeża. Pisał w nim tak: „(...) najdogodniejszym miejscem do budowy portu wojennego (jak również w razie potrzeby handlowego) jest Gdynia, a właściwie nizina między Gdynią a Oksywą, położoną w odległości 16 km od Nowego Portu w Gdańsku”."),
                ArticleElement.Image("drawable/historia1"),
                ArticleElement.Text("Zanim jednak Sejm uchwalił ustawę o budowie portu, nad zatoczkę u podnóża Kamiennej Góry i klifu w Orłowie przybywali na wczasy letnicy. Był wśród nich jeden z największych ówczesnych pisarzy polskich – Stefan Żeromski. W 1921 roku bacznie przyglądał się trwającym już robotom przy budowie tymczasowego portu wojennego i schroniska dla rybaków. W pracach tych które zainspirowały go do napisania książki „Wiatr od morza” dostrzegł początek nowej epoki, wielką szansę tego miejsca. W książce przedstawił jeszcze nieistniejący, ale, jak się niebawem okazało, zaskakująco wierny rzeczywistemu obraz portu i miasta.\n"),
                ArticleElement.Image("drawable/historia2"),
                ArticleElement.Text("W 1921 roku polskie władze przeprowadziły w Gdyni pierwszy spis powszechny. Wynikało z niego, że Gdynię zamieszkuje 1268 mieszkańców. Pięć lat później, kiedy miejscowość uzyskała prawa miejskie, było ich już 12 tys. Krótko przed wybuchem II wojny światowej Gdynię zamieszkiwało 127 tys. osób.\n"),
                ArticleElement.Text("23 września 1922 roku Sejm uchwalił ustawę „o budowie portu przy Gdyni na Pomorzu jako portu użyteczności publicznej”. Narodowa inwestycja, jaką była ostatecznie budowa portu w Gdyni, spłaciła się już przed II wojną światową, spełniając zarazem rolę katalizatora społecznej energii i patriotyzmu. Dzięki Gdyni Polacy uwierzyli, że stać ich na realizację najambitniejszych marzeń i wyzwań XX wieku.\n"),
                ArticleElement.Image("drawable/historia3"),
                ArticleElement.Text("10 lutego 1926 roku Gdynia uzyskała prawa miejskie, rozporządzeniem Rady Ministrów:  „(…) o zezwoleniu gminie wiejskiej Gdynia w powiecie wejherowskim w województwie pomorskiem na przyjęcie ustroju według pruskiej ordynacji miejskiej dla sześciu wschodnich prowincyj z dnia 30 maja 1853 r.”.")
            )
        )

        val article2 = Article(
            id = 1,
            title = "Łukasz Kobus nowym sekretarzem i dyrektorem Urzędu Miasta Gdyni",
            date = "2023-05-31",
            elements = listOf(
                ArticleElement.Image("drawable/artykul1"),
                ArticleElement.Text("Od 1 czerwca Łukasz Kobus obejmuje funkcję sekretarza miasta i dyrektora Urzędu Miasta Gdyni. Przedstawiamy sylwetkę nowego miejskiego urzędnika, który będzie zarządzać m.in. pracą gdyńskiego magistratu. "),
                ArticleElement.Text("Łukasz Kobus urodził się w 1985 roku. To rodowity gdynianin, jest żonaty, ma dwoje dzieci, z wykształcenia jest administratywistą. Zawodowo od początku związany jest z administracją publiczną, zarówno rządową jak i samorządową. \n" +
                        "\n" +
                        "Karierę urzędniczą rozpoczął w pionie cywilnym Policji, gdzie pracował m.in. jako kierownik sekcji w Komendzie Miejskiej Policji w Sopocie. Następnie był zatrudniony w Pomorskim Urzędzie Wojewódzkim w Gdańsku, gdzie zajmował się nadzorem nad działalnością jednostek samorządu terytorialnego. Pełnił tam również funkcję doradcy etycznego oraz egzekutora wojewody pomorskiego. \n" +
                        "\n" +
                        "Od blisko 10 lat jest zatrudniony w samorządzie. Pełnił funkcję sekretarza gminy w Kolbudach, a od 2018 roku sekretarza miasta w Słupsku. Jest wieloletnim współpracownikiem Krajowego Biura Wyborczego, na którego zlecenie prowadził liczne szkolenia z zakresu prawa wyborczego. \n" +
                        "\n" +
                        "Łukasz Kobus jest również zaangażowany w działalność społeczną. Wielokrotnie powoływano go w skład Inspekcji Wyborczej, w ramach której nadzorował gminy naszego województwa w zakresie organizacji i przeprowadzenia wyborów. Przez wiele lat był również kuratorem sądowym Sądów Rejonowych w Gdyni oraz w Sopocie.")
            )
        )

        val article3 =Article(
            id =2,
            title ="10 ton w jeden weekend – kto śmieci na gdyńskich plażach?",
            date ="2023-05-31",
            elements = listOf(
                ArticleElement.Image("drawable/artykul2"),
                ArticleElement.Text("Gdyńskie plaże są jedną z najlepszych wizytówek miasta z morza i marzeń. Są popularnym wyborem jako miejsce wypoczynku i spotkań zarówno mieszkańców, jak i turystów. Mimo, iż plaża w sezonie sprzątana jest kilka razy dziennie, często wraca pytanie: „Skąd tyle śmieci?”. To jedna z najczęściej pojawiających się kwestii kierowanych do organizatora kąpielisk. Tylko w jeden weekend na piasku w Śródmieściu potrafi się pojawić aż 10 ton odpadów!"),
                ArticleElement.Text("Od początku roku do połowy maja z gdyńskich plaż uprzątnięto blisko 40 ton odpadów\n" +
                        "Sprzątanie plaż jest codziennym procesem wykonywanym nawet przez 20 osób\n" +
                        "Zwiększanie koszów nie jest drogą do rozwiązaniu problemu\n" +
                        "Najważniejsza jest edukacja i zmiana zachowań społecznych"),
                ArticleElement.Text("Butelki największą częścią odpadów"),
                ArticleElement.Text("Plastikowe butelki, opakowania po jedzeniu, puszki, a nawet niebezpieczne odpady, takie jak strzykawki czy fragmenty szkła, to niestety codzienny widok na plażach. Problem nasila się szczególnie w sezonie letnim, kiedy liczba odwiedzających gwałtownie rośnie.\n" +
                        "Od 1 stycznia 2024 r. do 15 maja 2024 r. zebrano z plaż w Gdyni łącznie 36,82 tony odpadów, w tym 14,04 ton komunalnych, 1,72 tony ulegających biodegradacji oraz 21,06 ton szklanych. Sezon wakacyjny dopiero przed nami, a powyższe wartości już są zatrważające. Warto przypomnieć, że jednym z niechlubnych rekordów jest weekend, podczas którego w minionym roku odbywały się Cudawianki. Tylko w dniach 24-25.06.2023 zebrano rekordowe 10 ton odpadów! W sezonie letnim od czerwca do sierpnia liczba ta przekroczyła sto ton!"),
                ArticleElement.Image("drawable/artykul32")



            )
        )



        dbHelper.insertArticle(article1)
        dbHelper.insertArticle(article2)
        dbHelper.insertArticle(article3)
    }
}
