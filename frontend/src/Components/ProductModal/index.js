import React from 'react'
import { api } from '../../Helpers/api'
import * as C from "./styles"

const ProductModal = ({product}) => {

    const [description, setDescription] = React.useState();
    const [name, setName] = React.useState();
    const [price, setPrice] = React.useState();


    const handleDelete = async () => {

        api.deleteProduct(product.id);
        window.location.href = "/categorias"
    }

    const handleEdit = async () => {

        api.editProduct(product.id, description, name, +price, product.imgUrl);
        window.location.href = `/categorias/${product.id}`
    }

  return (
    <C.Container>
        <h3>VOCÊ EDITANDO O PRODUTO: {product.name}</h3>
        <p>Nome:</p> <input type="text" value={name} onChange={(e) => setName(e.target.value)}/>
        <p>Descrição:</p> <input type="text" value={description} onChange={(e) => setDescription(e.target.value)}/>
        <p>Preço:</p> <input type="number" value={price} onChange={(e) => setPrice(e.target.value)}/>
        <button onClick={handleEdit}>EDITAR</button>
        <button onClick={handleDelete}>DELETAR</button>
    </C.Container>
  )
}

export default ProductModal