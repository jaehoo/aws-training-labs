import React from 'react'

const ProductTable = props => (
    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Amount</th>
                <th>Price</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            {props.products.length > 0 ? (
                props.products.map(product => (
                    <tr key={product.id}>
                        <td>{product.name}</td>
                        <td>{product.description}</td>
                        <td>{product.amount}</td>
                        <td>{product.price}</td>
                        <td>
                            <button onClick={() => {props.editRow(product)}} className="button muted-button">Edit</button>
                            {props.deleting && props.deleteId === product.id ? (
                                <button className="button muted-button">Deleting...</button>
                            ):(
                                <button onClick={() => props.deleteProduct(product.id)}  className="button muted-button">Delete</button>

                            )}
                        </td>
                    </tr>
                ))
            ) : (
                <tr>
                    <td colSpan={3}>No Products</td>
                </tr>
            )}
        </tbody>
    </table>
)

export default ProductTable