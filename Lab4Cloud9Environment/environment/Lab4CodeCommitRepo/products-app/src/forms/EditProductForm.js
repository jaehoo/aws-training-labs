import React, { useState, useEffect } from 'react'


const EditProductForm = props => {



  const [product, setProduct] = useState(props.currentProduct)
  useEffect(() => {
    setProduct(props.currentProduct)
  }, [props])

  const handleInputChange = event => {
    const { name, value } = event.target

    setProduct({ ...product, [name]: value })
  }

  async function updateProducts(product) {
    const url = process.env.REACT_APP_PROD_URL+'/api/products/update'
    const response = await fetch(url, 
        {
            mode: 'cors',
            method: 'POST',
            headers: {
                "Accept":"application/json"
            },
            body: JSON.stringify(product)
        });
    console.log(response.status);
    return response.status
}

  return (
    <form
      onSubmit={event => {
        event.preventDefault()
        if (!isNaN(product.price) && !isNaN(product.amount)){
          updateProducts(product).then( result => {
            if(result === 200){
              props.updateProduct(product.id, product)
              alert("Product successfully updated");
            }else{
              console.log("Error:"+result)
              alert("A Error has occurred");
            }
          }
        )
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
      <button>Update Product</button>
      <button onClick={() => props.setEditing(false)} className="button muted-button">
        Cancel
      </button>
    </form>
  )
}

export default EditProductForm