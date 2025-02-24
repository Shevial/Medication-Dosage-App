import './App.css'
import ButtonComponent from './components/ButtonComponent'
import HeaderComponents from './components/HeaderComponents'
import Login from './components/login'; // Asegúrate de que esta línea esté presente

import { useState } from 'react'

function App() {

  // let number = 0; // variable sin reactividad
  const [number, setNumber] = useState(0); // variable con reactividad // 
  const [myValue, setMyValue] = useState(""); //usesState para agregar logica
  let myPlaceholder = "Escribe algo";

  const [greetings, setGreetings] = useState("Bienvenidos a mi web");
  const links = {
    home : "Homes",
    blog : "Blogs",
    news : "Newss",
    contactus : "Contact wUs"
  }
  const [user, setUser] = useState({})

const login = (userInfo) => {
  console.log(userInfo);
  setUser(userInfo);
}

  const addOne = () => {
   // number++;
   setNumber(number + 1);
    console.log(number);
  }

  const sayHello = () => {
    console.log("Hello ");
  }
  const handleChange = (e) => { 
    console.log(e.target.value); // recoger valor del input 
  }

  return (
    <>
      <HeaderComponents greetings={greetings} links= {links}></HeaderComponents>
      <main className='main-content'>
        <h2 onClick={sayHello}>Hello {user.username}</h2>
        <Login handleLogin={login}></Login>

        <h2 onClick={addOne}>Number: {number}</h2>     {/* mostrar variable */}

{/* importante */}
      <input value={myValue} placeholder={myPlaceholder} type="text" onChange={handleChange} />

      <br />
      <br />
      <ButtonComponent></ButtonComponent>
    </main>
    </>
  )
}

export default App
