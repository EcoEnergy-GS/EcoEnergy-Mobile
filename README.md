# EcoEnergy
O projeto visa incentivar a economia de energia elétrica em residências por meio da integração de um dispositivo inteligente à rede elétrica, capaz de monitorar o consumo mensal de eletricidade. A plataforma coleta e processa os dados, oferecendo informações claras sobre os padrões de consumo e alertando sobre possíveis excessos, com o objetivo de promover maior conscientização ambiental.
Uma das funcionalidades principais do projeto é a gamificação, que torna o processo de economia de energia mais atrativo. Após um período inicial de monitoramento, o sistema calcula a média de consumo mensal do usuário e recompensa com pontos sempre que o consumo diminui em relação a essa média. Isso estimula a redução do gasto de energia de maneira interessante e personalizada, tornando a experiência mais motivadora para o usuário.
Além de incentivar a economia de energia, a plataforma permite que o usuário insira dados detalhados sobre sua residência, como a quantidade de pessoas, os eletrodomésticos presentes e o endereço. Essas informações são fundamentais para o funcionamento da nossa IA, que analisa os dados e gera previsões de consumo, ajudando o usuário a entender e controlar seu impacto energético de maneira mais eficaz.
O sistema também oferece recompensas, onde as empresas podem cadastrar bonificações visíveis aos usuários. Isso não só oferece visibilidade para as empresas, mas também as ajuda a aumentar o consumo de seus produtos, já que os usuários são motivados a buscar essas recompensas ao reduzir o consumo de energia. O cadastro de recompensas permite que os pontos sejam trocados de forma prática e eficiente, criando um ciclo contínuo de engajamento entre usuários e empresas.
O sistema está estruturado para ser acessível tanto pelo dispositivo móvel quanto pelo site, proporcionando uma experiência integrada e completa, com a missão de transformar o hábito de economizar energia em um desafio divertido e recompensador.
## Membros:
- Gabriel Siqueira Rodrigues RM:98626
- Gustavo de Oliveira Azevedo RM:550548
- Isabella Jorge Ferreira RM:552329
- Mateus Mantovani Araújo RM:98524
- Juan de Godoy RM:551408

## Estrutura de pastas
<pre>
  .
└── App/
    ├── manifests/
    │   └── AndroidManifests.xml
    ├── kotlin+java/
    │   └── br/
    │       └── com/
    │           └── vom/
    │               └── hive/
    │                   ├── model/
    │                   │   ├── Product
    │                   │   └── Residence
    │                   ├── recyclerView/
    │                   │   └── adapter/
    │                   │       ├── ProductAdapter
    │                   │       └── ResidenceAdapter
    │                   ├── AboutActivity
    │                   ├── EditResidenceActivity
    │                   ├── HomeActivity
    │                   ├── LoginActivity
    │                   ├── MainActivity
    │                   ├── RegisterActivity
    │                   ├── RegisterResidenceActivity
    │                   ├── ResidenceActivity
    │                   └── StoreActivity
    └── res/
        ├── drawable/
        ├── font/
        ├── layout/
        │   ├── activity_about.xml
        │   ├── activity_home.xml
        │   ├── activity_login.xml
        │   ├── activity_main.xml
        │   ├── activity_register.xml
        │   ├── activity_register_residence.xml
        │   ├── activity_residence.xml
        │   ├── activity_store.xml
        │   ├── header.xml
        │   ├── item_product.xml
        │   └── item_residence.xml
        ├── mipmap/
        ├── values/
        └── xml/
</pre>

## Demonstração do app
- https://youtu.be/uKAYNhx3YwA
