<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<br />

<!-- PROJECT LOGO -->
<div align="center">
  <img src="images/big-data-logo.png" alt="Logo" width="450" height="250">
  <h3 align="center">VIEW#BIGDATA</h3>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project
This project addresses the problems in the visualizations of the extended 3V model of Big Data.
The objective is the definition of a an interactive system for visualizing Big Data managed through a graph NoSQL database model.
This prototype allows the user to interactively visualize the contents of the graph database and focus on each node and it's associated links, while inspecting the node.

### Built With
* ![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)
* ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
* ![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
* ![Neo4J](https://img.shields.io/badge/Neo4j-008CC1?style=for-the-badge&logo=neo4j&logoColor=white)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

In order to run this project. You will need to run the different components that are required to run the project successfully. The project is composed of 3 components:
* 1 - Frontend: The component that is responsible for the UI.

* 2 - Backend: The component that is responsible for interacting with the database and it provides REST API to be consumed by the frontend or even other external applications

* 3 - Neo4J: It is a NoSql graph based database on which data is stored

The steps to run each components has been specified below:

### Prerequisites

NEO4J Database
<br />
Below are the steps to run neo4j using docker. If you want to run neo4j without docker you can refer to the instructions <a href="https://neo4j.com/docs/operations-manual/current/installation/">here</a>.
<br />
Please follow the following steps to create and run neo4j docker container:

1- For the first time, make sure that you comment the NEO4J_AUTH environment variable for the neo4j service in the docker-compose.yml file by adding # .

```shell
neo4j:
    image: neo4j:5.16.0
    container_name: neo4j
    restart: always
    environment:
      #NEO4J_AUTH: neo4j/some_random_password
    ports:
      - 7474:7474
      - 7687:7687
    volumes:
      - neo4j_db_data:/data
```
<br/>
2- Run the following command:

```shell
docker compose up -d
```

Note: if you face docker compose command not found error in the terminal, add the dash symbol between the docker and compose. This usually happens based on the OS. Don't worry if you get an error related to the springboot app at this stage since neo4j configuration is not complete yet.

```shell
docker-compose up -d
```

<br/>
3- By default, the username and password is neo4j. You will be required to change it by accessing the Neo4J browser 
interface <a href="http://localhost:7474/browser/">here</a> . Log in by enter the default username and password and then set your new password.
<br/>

Note: The Neo4J browser URL is the default url that is running in your machine. Make sure that you change the URL for the Neo4J interface if you are running it on a different port number.

4- Stop docker container by using the following command:

```shell
docker compose down
```
<br/>
5- Uncomment the NEO4J_AUTH environment variable for the neo4j service in the docker-compose.yml file and put the new password you have set earlier by replacing your_new_password with the new password:

```shell
neo4j:
    image: neo4j:5.16.0
    container_name: neo4j
    restart: always
    environment:
      NEO4J_AUTH: neo4j/your_new_password
    ports:
      - 7474:7474
      - 7687:7687
    volumes:
      - neo4j_db_data:/data
```

<br/>
### Installation

_Below is an example of how you can instruct your audience on installing and setting up your app. This template doesn't rely on any external dependencies or services._

1. Get a free API Key at [https://example.com](https://example.com)
2. Clone the repo
   ```sh
   git clone https://github.com/your_username_/Project-Name.git
   ```
3. Install NPM packages
   ```sh
   npm install
   ```
4. Enter your API in `config.js`
   ```js
   const API_KEY = 'ENTER YOUR API';
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [x] Add Changelog
- [x] Add back to top links
- [ ] Add Additional Templates w/ Examples
- [ ] Add "components" document to easily copy & paste sections of the readme
- [ ] Multi-language Support
    - [ ] Chinese
    - [ ] Spanish

See the [open issues](https://github.com/othneildrew/Best-README-Template/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Your Name - [@your_twitter](https://twitter.com/your_username) - email@example.com

Project Link: [https://github.com/your_username/repo_name](https://github.com/your_username/repo_name)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

Use this space to list resources you find helpful and would like to give credit to. I've included a few of my favorites to kick things off!

* [Choose an Open Source License](https://choosealicense.com)
* [GitHub Emoji Cheat Sheet](https://www.webpagefx.com/tools/emoji-cheat-sheet)
* [Malven's Flexbox Cheatsheet](https://flexbox.malven.co/)
* [Malven's Grid Cheatsheet](https://grid.malven.co/)
* [Img Shields](https://shields.io)
* [GitHub Pages](https://pages.github.com)
* [Font Awesome](https://fontawesome.com)
* [React Icons](https://react-icons.github.io/react-icons/search)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/othneildrew/Best-README-Template/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/othneildrew/Best-README-Template/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/othneildrew
[product-screenshot]: images/screenshot.png
