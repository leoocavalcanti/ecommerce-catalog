import React from 'react'
import { BrowserRouter } from 'react-router-dom'
import Header from './Components/Header'
import Pages from './Pages'

const App = () => {
  return (
    <div>
    <BrowserRouter>
    <Header/>
    <Pages/>
    </BrowserRouter>
    </div>
  )
}

export default App