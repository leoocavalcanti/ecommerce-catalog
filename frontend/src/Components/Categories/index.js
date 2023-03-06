import React from 'react'
import * as C from "./styles"
import { api } from '../../Helpers/api';
import { Link } from 'react-router-dom';

const Categories = () => {

    const [products, setProducts] = React.useState(null);

    React.useEffect(() => {

        const getProducts = async () => {

            const json = await api.getAllProducts();
            
            if(!json.error){
                setProducts(json);
                return;
            }


        }

        getProducts();

    }, []);

  return (
    <div>
        <C.Container>

            {products && products.content.map((item, index) => (

            <div key={index}>
                <Link to={`${item.id}`}>{item.name} - {item.id}</Link>
            </div>
            ))}


        </C.Container>
    </div>
  )
}

export default Categories