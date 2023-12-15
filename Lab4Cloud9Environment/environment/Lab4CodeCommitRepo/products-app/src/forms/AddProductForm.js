import React, { useState } from 'react'

const AddProductForm = props => {
    const initialFormState = { id: null, name: '', description: '', amount:'', price: '' }
    const [product, setProduct] = useState(initialFormState)

    const handleInputChange = event => {
        const { name, value } = event.target
        setProduct({ ...product, [name]: value })
    }

    async function insertProducts(product) {
        const url = process.env.REACT_APP_PROD_URL+'/api/products/insert'
        const response = await fetch(url, 
            {
                mode: 'cors',
                method: 'POST',
                headers: {
                    "Accept":"application/json"
                },
                body: JSON.stringify(product)
            });
        return response;
    } 

    return (
        <form
            onSubmit={event => {
                event.preventDefault()
                if (!product.name || !product.description || !product.amount || !product.price) return
                    if(!isNaN(product.price) && !isNaN(product.amount)){
                        insertProducts(product).then(result => {
                            //console.log(await response.json())
                            if(result.status === 200){
                                result.json()
                                .then(json => {
                                    console.log(json)
                                    product.id = json.productId;
                                    props.addProduct(product)
                                    alert("Product successfully added");
                                })
                                setProduct(initialFormState)
                            }else{
                                alert("A Error has occured");
                                console.log("Error:"+result)
                            }
                        })
                    }else{
                        alert("Please, use numbers to define price and/or amount values")
                    }

            }}
        >
            <label>Name</label>
            <input type="text" name="name" value={product.name} onChange={handleInputChange} />
            <label>Description</label>
            <input type="text" name="description" value={product.description} onChange={handleInputChange} />
            <label>Amount</label>
            <input type="text" name="amount" value={product.amount} onChange={handleInputChange} />
            <label>Price</label>
            <input type="text" name="price" value={product.price} onChange={handleInputChange} />
            <button>Add new product</button>
        </form>
    )
}

export default AddProductForm