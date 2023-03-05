import React from 'react'
import Nav from '../Nav';
import * as C from "./styles"

const Header = () => {
  
  return (
    <C.Container>
      <C.Logo>
        <h2>Catálogo Produtos</h2>
      </C.Logo>
      <C.Categories>  
        <ul>
          <Nav link="/" name="HOME"/>
          <Nav link="/categorias" name="CATEGORIAS"/>
          <Nav link="/login" name="LOGIN"/>
        </ul>
      </C.Categories>
    </C.Container>
  )
}

export default Header