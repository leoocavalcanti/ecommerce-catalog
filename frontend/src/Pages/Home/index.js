import React from 'react'
import * as C from "./styles.js"
import {ReactComponent as HomePhoto} from "../../Assets/homephoto.svg"
import {ReactComponent as HomeButton} from "../../Assets/homebutton.svg"
import { Link } from 'react-router-dom'

const Home = () => {
  return (
    <C.Container>
      <C.HomeArea>
        <C.TextHomeArea>
          <h1>Conheça o melhor catálogo de produtos</h1>
          <p>Ajudaremos você a encontrar os melhores produtos disponíveis no mercado.</p>
          <Link to="/categorias">
            <HomeButton/>
          </Link>
        </C.TextHomeArea>
        <C.HomePhoto>
            <HomePhoto style={{width: 648, height: 481}}/>
        </C.HomePhoto>
      </C.HomeArea>
    </C.Container>
  )
}

export default Home