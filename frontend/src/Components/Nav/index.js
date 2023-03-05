import React from 'react'
import * as C from "./styles"
import { NavLink, useLocation } from 'react-router-dom'

const Nav = ({link, name}) => {

    const location = useLocation();
    let isActive = link === location.pathname;

  return (
    <C.Container active={isActive}>
        <NavLink to={link}><li>{name}</li></NavLink>
    </C.Container>
  )
}

export default Nav