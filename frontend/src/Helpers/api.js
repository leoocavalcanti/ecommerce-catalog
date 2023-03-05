let url = "http://localhost:8080";

export const api = {

    getAllCategories: async () => {

            const req = await fetch(url+"/categories");
            const json = await req.json();
            return json;
        
    },

    getAllProducts: async () => {

        try{
            const req = await fetch("http://localhost:8080/products?page=1");
            const json = await req.json();
            return json;
        }
        catch(e){

            return e.message;
        }
    },

    getProduct: async (id) => {

        try{
            const req = await fetch(`http://localhost:8080/products/${id}`);
            const json = await req.json();
            return json;
        }
        catch(e){

            return e.message;
        }
    },

    deleteProduct: async (id) => {

        try{
            const req = await fetch(`http://localhost:8080/products/${id}`, {

                method: "DELETE"
            });
            const json = await req.json();
            return json;
        }
        catch(e){

            return e.message;
        }
    },

    editProduct: async (id, description, name, price, imgUrl) => {

        try{
            const req = await fetch(`http://localhost:8080/products/${id}`, {

                method: "PUT",
                headers: {

                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    "date": "2020-07-20T10:00:00Z",
                    "description": description,
                    "name": name,
                    "imgUrl": imgUrl,
                    "price": price,
                    "categories": [
                      {
                        "id": 1
                      },
                      {
                        "id": 3
                      }
                    ]
                  })
            });
            const json = await req.json();
            return json;
        }
        catch(e){

            return e.message;
        }
    },


        
}