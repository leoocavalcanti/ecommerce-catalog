import React from 'react'
import { Routes, Route } from 'react-router-dom'
import Catalog from './Catalog'
import Home from './Home'
import Product from './Product'

const Pages = () => {
  return (
    <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/categorias" element={<Catalog/>}/>
        <Route path="/categorias/:id" element={<Product/>}/>
    </Routes>
  )
}

export default Pages