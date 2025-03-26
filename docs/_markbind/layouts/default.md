<head>
  <title>{{ title }}</title>
  <!-- ...other meta tags... -->
</head>
<head-bottom>
  <link rel="stylesheet" href="{{baseUrl}}/stylesheets/main.css">
</head-bottom>

<header sticky>
  <navbar type="dark">
    <a slot="brand" href="{{baseUrl}}/index.html" title="{{ title }}" class="navbar-brand">{{ title }}</a>
    <li><a href="{{baseUrl}}/index.html" class="nav-link">Home</a></li>
    <li><a href="{{baseUrl}}/UserGuide.html" class="nav-link">User Guide</a></li>
    <li><a href="{{baseUrl}}/DeveloperGuide.html" class="nav-link">Developer Guide</a></li>
    <li><a href="{{baseUrl}}/AboutUs.html" class="nav-link">About Us</a></li>
    <li><a href="https://github.com/AY2425S2-CS2103T-T11-4/tp.git" target="_blank" class="nav-link"><md>:fab-github:</md></a>
    </li>
    <li slot="right">
      <form class="navbar-form">
        <searchbar :data="searchData" placeholder="Search" :on-hit="searchCallback" menu-align-right></searchbar>
      </form>
    </li>
  </navbar>
</header>

<div id="flex-body">
  <nav id="site-nav">
    <div class="site-nav-top">
      <div class="fw-bold mb-2" style="font-size: 1.25rem;">FinClient Tutorial</div>
    </div>
    <div class="nav-component slim-scroll">
      <site-nav>
* [Home]({{ baseUrl }}/index.html) :expanded:
  * [Introduction]({{ baseUrl }}/index.html#introduction)
  * [Example usages]({{ baseUrl }}/index.html#example-usages)
  * [Value Proposition]({{ baseUrl }}/index.html#value-proposition)
  * [Acknowledgment and Contribution]({{ baseUrl }}/index.html#acknowledgment-and-contribution)
* [User Guide]({{ baseUrl }}/UserGuide.html) :expanded:
  * [Quick Start]({{ baseUrl }}/UserGuide.html#quick-start)
  * [Features]({{ baseUrl }}/UserGuide.html#features) :expanded:
    * [Viewing Help]({{ baseUrl }}/UserGuide.html#viewing-help)
    * [Adding a Person]({{ baseUrl }}/UserGuide.html#adding-a-person)
    * [Listing all Persons]({{ baseUrl }}/UserGuide.html#listing-all-persons)
    * [Editing a Person]({{ baseUrl }}/UserGuide.html#editing-a-person)
    * [Locating Persons by Name]({{ baseUrl }}/UserGuide.html#locating-persons-by-name)
    * [Deleting a Person]({{ baseUrl }}/UserGuide.html#deleting-a-person)
    * [Hiding a Person]({{ baseUrl }}/UserGuide.html#hiding-a-person)
    * [Revealing a Person]({{ baseUrl }}/UserGuide.html#revealing-a-person)
    * [Adding Remarks]({{ baseUrl }}/UserGuide.html#adding-remarks)
    * [Sorting Contacts]({{ baseUrl }}/UserGuide.html#sorting-contacts)
    * [Clearing all Entries]({{ baseUrl }}/UserGuide.html#clearing-all-entries)
    * [Exiting the Program]({{ baseUrl }}/UserGuide.html#exiting-the-program)
    * [Saving the Data]({{ baseUrl }}/UserGuide.html#saving-the-data)
    * [Editing the Data File]({{ baseUrl }}/UserGuide.html#editing-the-data-file)
    * [Archiving Data Files]({{ baseUrl }}/UserGuide.html#archiving-data-files)
  * [FAQ]({{ baseUrl }}/UserGuide.html#faq)
  * [Known Issues]({{ baseUrl }}/UserGuide.html#known-issues)
  * [Command Summary]({{ baseUrl }}/UserGuide.html#command-summary)
* [Developer Guide]({{ baseUrl }}/DeveloperGuide.html) :expanded:
  * [Acknowledgements]({{ baseUrl }}/DeveloperGuide.html#acknowledgements)
  * [Setting Up]({{ baseUrl }}/DeveloperGuide.html#setting-up-getting-started)
  * [Design]({{ baseUrl }}/DeveloperGuide.html#design)
  * [Implementation]({{ baseUrl }}/DeveloperGuide.html#implementation)
  * [Documentation, logging, testing, configuration, dev-ops]({{ baseUrl }}/DeveloperGuide.html#documentation-logging-testing-configuration-dev-ops)
  * [Appendix: Requirements]({{ baseUrl }}/DeveloperGuide.html#appendix-requirements)
  * [Appendix: Instructions for manual testing]({{ baseUrl }}/DeveloperGuide.html#appendix-instructions-for-manual-testing)
* [About Us]({{ baseUrl }}/AboutUs.html)
      </site-nav>
    </div>
  </nav>
  <div id="content-wrapper">
    {{ content }}
  </div>
  <nav id="page-nav">
    <div class="nav-component slim-scroll">
      <page-nav />
    </div>
  </nav>
  <scroll-top-button></scroll-top-button>
</div>

<footer>
  <!-- Support MarkBind by including a link to us on your landing page! -->
  <div class="text-center">
    <small>[<md>**Powered by**</md> <img src="https://markbind.org/favicon.ico" width="30"> {{MarkBind}}, generated on {{timestamp}}]</small>
  </div>
</footer>
