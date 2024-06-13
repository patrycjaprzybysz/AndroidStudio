
package com.example.projekt

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class ArticleDetailFragment : Fragment() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var articles: List<Article> // Lista artykułów
    private lateinit var article: Article // Wybrany artykuł do wyświetlenia

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article_detail, container, false)
        dbHelper = DatabaseHelper(requireContext())


        articles = listOf(
            Article(
                id = 1,
                title = "Łukasz Kobus nowym sekretarzem i dyrektorem Urzędu Miasta Gdyni",
                date = "2023-05-31",
                elements = listOf(
                    ArticleElement.Image("drawable/artykul1"),
                    ArticleElement.Text("Od 1 czerwca Łukasz Kobus obejmuje funkcję sekretarza miasta i dyrektora Urzędu Miasta Gdyni. Przedstawiamy sylwetkę nowego miejskiego urzędnika, który będzie zarządzać m.in. pracą gdyńskiego magistratu. "),
                    ArticleElement.Text(
                        "Łukasz Kobus urodził się w 1985 roku. To rodowity gdynianin, jest żonaty, ma dwoje dzieci, z wykształcenia jest administratywistą. Zawodowo od początku związany jest z administracją publiczną, zarówno rządową jak i samorządową. \n" +
                                "\n" +
                                "Karierę urzędniczą rozpoczął w pionie cywilnym Policji, gdzie pracował m.in. jako kierownik sekcji w Komendzie Miejskiej Policji w Sopocie. Następnie był zatrudniony w Pomorskim Urzędzie Wojewódzkim w Gdańsku, gdzie zajmował się nadzorem nad działalnością jednostek samorządu terytorialnego. Pełnił tam również funkcję doradcy etycznego oraz egzekutora wojewody pomorskiego. \n" +
                                "\n" +
                                "Od blisko 10 lat jest zatrudniony w samorządzie. Pełnił funkcję sekretarza gminy w Kolbudach, a od 2018 roku sekretarza miasta w Słupsku. Jest wieloletnim współpracownikiem Krajowego Biura Wyborczego, na którego zlecenie prowadził liczne szkolenia z zakresu prawa wyborczego. \n" +
                                "\n" +
                                "Łukasz Kobus jest również zaangażowany w działalność społeczną. Wielokrotnie powoływano go w skład Inspekcji Wyborczej, w ramach której nadzorował gminy naszego województwa w zakresie organizacji i przeprowadzenia wyborów. Przez wiele lat był również kuratorem sądowym Sądów Rejonowych w Gdyni oraz w Sopocie."
                    )
                )
            ),
            Article(
                id = 2,
                title = "10 ton w jeden weekend – kto śmieci na gdyńskich plażach?",
                date = "2023-05-31",
                elements = listOf(
                    ArticleElement.Image("drawable/artykul2"),
                    ArticleElement.Text("Gdyńskie plaże są jedną z najlepszych wizytówek miasta z morza i marzeń. Są popularnym wyborem jako miejsce wypoczynku i spotkań zarówno mieszkańców, jak i turystów. Mimo, iż plaża w sezonie sprzątana jest kilka razy dziennie, często wraca pytanie: „Skąd tyle śmieci?”. To jedna z najczęściej pojawiających się kwestii kierowanych do organizatora kąpielisk. Tylko w jeden weekend na piasku w Śródmieściu potrafi się pojawić aż 10 ton odpadów!"),
                    ArticleElement.Text(
                        "Od początku roku do połowy maja z gdyńskich plaż uprzątnięto blisko 40 ton odpadów\n" +
                                "Sprzątanie plaż jest codziennym procesem wykonywanym nawet przez 20 osób\n" +
                                "Zwiększanie koszów nie jest drogą do rozwiązaniu problemu\n" +
                                "Najważniejsza jest edukacja i zmiana zachowań społecznych"
                    ),
                    ArticleElement.Text("Butelki największą częścią odpadów"),
                    ArticleElement.Text(
                        "Plastikowe butelki, opakowania po jedzeniu, puszki, a nawet niebezpieczne odpady, takie jak strzykawki czy fragmenty szkła, to niestety codzienny widok na plażach. Problem nasila się szczególnie w sezonie letnim, kiedy liczba odwiedzających gwałtownie rośnie.\n" +
                                "Od 1 stycznia 2024 r. do 15 maja 2024 r. zebrano z plaż w Gdyni łącznie 36,82 tony odpadów, w tym 14,04 ton komunalnych, 1,72 tony ulegających biodegradacji oraz 21,06 ton szklanych. Sezon wakacyjny dopiero przed nami, a powyższe wartości już są zatrważające. Warto przypomnieć, że jednym z niechlubnych rekordów jest weekend, podczas którego w minionym roku odbywały się Cudawianki. Tylko w dniach 24-25.06.2023 zebrano rekordowe 10 ton odpadów! W sezonie letnim od czerwca do sierpnia liczba ta przekroczyła sto ton!"
                    ),
                    ArticleElement.Image("drawable/artykul32")
                )
            )
        )

        // Odczytanie articleId z argumentów
        val articleId = arguments?.getLong("articleId", -1L) ?: -1L

        // Znalezienie artykułu na podstawie articleId
        article = articles.firstOrNull { it.id == articleId }
            ?: throw IllegalArgumentException("Artykuł o podanym id nie został znaleziony.")

        // Wyświetlenie tytułu artykułu
        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        textViewTitle.text = article.title

        // Wyświetlenie daty artykułu
        val textViewDate: TextView = view.findViewById(R.id.textViewDate)
        textViewDate.text = article.date

        // Pobranie kontenera, do którego będziemy dodawać treści
        val container: LinearLayout = view.findViewById(R.id.container)

        // Wyświetlenie zawartości artykułu
        for (element in article.elements) {
            when (element) {
                is ArticleElement.Text -> {
                    val textView = TextView(requireContext()).apply {
                        text = element.content
                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).apply {
//                            setMargins(0, 0, 0, resources.getDimensionPixelSize(R.dimen.text_margin_bottom))
                        }
                    }
                    container.addView(textView)
                }
                is ArticleElement.Image -> {
                    val imageView = ImageView(requireContext()).apply {
                        setImageResource(resources.getIdentifier(element.imagePath, "drawable", requireContext().packageName))
                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).apply {
                            gravity = Gravity.CENTER
//                            setMargins(0, 0, 0, resources.getDimensionPixelSize(R.dimen.image_margin_bottom))
                        }
                        adjustViewBounds = true
                    }
                    container.addView(imageView)
                }
            }
        }

        return view
    }
}




