import React, { useState } from 'react';
import ProductTable from './tables/ProductTable'
import AddProductForm from './forms/AddProductForm'
import { useGetProducts } from './dao/getProducts';
import EditProductForm from './forms/EditProductForm';

const App = () => {

  const [products, loading, setProducts] = useGetProducts();

  const addProduct = product => {
    setProducts([...products, product])
  }

  const [editing, setEditing] = useState(false)
  const [deleting, setDeleting] = useState(false)
  const [deleteId, setDeleteId] = useState(null)
  const initialFormState = { id: null, name: '', description: '', amount: '', price: '' }

  const [currentProduct, setCurrentProduct] = useState(initialFormState)

  async function deleteProducts(product) {
    const url = process.env.REACT_APP_PROD_URL+'/api/products/delete'
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
  const deleteProduct = id => {
    setDeleting(true);
    setDeleteId(id);
    const deleteItem = { id: id }
    if (window.confirm("Are you sure to delete this item?")) {
      deleteProducts(deleteItem).then(result => {
        if(result === 200){
          setProducts(products.filter(product => product.id !== id))
          alert("Product successfully deleted");
          setDeleting(false);
        }else{
          alert("A Error has occurred");
          console.log("Error:"+result)
        }
      }
      )
    } else {
      setDeleting(false);
    } 
  }

  const editRow = product => {
    setEditing(true)
  
    setCurrentProduct({ id: product.id, name: product.name, description: product.description, amount: product.amount, price: product.price })
  }

  const updateProduct = (id, updatedProduct) => {
    setEditing(false)
  
    setProducts(products.map(product => (product.id === id ? updatedProduct : product)))
  }

  return (
    <div className="container">
          <h1>CRUD App with Hooks</h1>
          <div className="flex-row">
          <div className="flex-large">
  {editing ? (
    <div>
      <h2>Edit Product</h2>
      <EditProductForm
        editing={editing}
        setEditing={setEditing}
        currentProduct={currentProduct}
        updateProduct={updateProduct}
      />
    </div>
  ) : (
    <div>
      <h2>Add Product</h2>
      <AddProductForm addProduct={addProduct} />
    </div>
  )}
</div>
            <div className="flex-large">
              <h2>View products</h2>
              {loading ? (
                "Loading..."
              ):(
                <ProductTable products={products} editRow={editRow} deleteId={deleteId} deleting={deleting} deleteProduct={deleteProduct}/>
              )}
          </div>
        </div>
    </div>
  );
}

export default App;
