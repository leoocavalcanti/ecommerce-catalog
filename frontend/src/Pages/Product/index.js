import React from 'react'
import { useParams } from 'react-router-dom'
import ProductModal from '../../Components/ProductModal';
import { api } from '../../Helpers/api';

const Product = () => {

    const {id} = useParams();

    const [product, setProduct] = React.useState(null);
    const [modal, setModal] = React.useState(false);

    React.useEffect(() => {

      const getProductById = async () => {

        const json = await api.getProduct(id);
        setProduct(json);


      }

      getProductById();

    }, []);

    console.log(product);

    if(!product) return null;

  return (
    <div>
      <h1>{product.name} - {product.price}</h1>
      <p>{product.description}</p>
      <img src={product.imgUrl} alt={product.name}/>
        <button onClick={() => setModal(true)}>Editar</button>

        {modal && 
        <>
        
          <ProductModal product={product}/>

        </>
        }

    </div>
  )
}

export default Product