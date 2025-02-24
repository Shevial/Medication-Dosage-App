import "./HeaderComponents.css";


function HeaderComponents(props) {
  const {greetings, links } = props;
  return (
    <header className="header">
        <h1 className="title">{greetings}</h1>
      <nav>
        <ul className="header-list">
            <li>
                <a className= "links" href="/">{links.home}</a>
            </li>
            <li>
                <a className= "links" href="/">{links.blog}</a>
            </li>
            <li>
                <a className= "links" href="/">{links.news}</a>
            </li>
            <li>
                <a className= "links" href="/">{links.contactus}</a>
            </li>
        </ul>
      </nav>
    </header>
  );
}

export default HeaderComponents;