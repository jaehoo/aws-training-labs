import { useState, useEffect } from "react";

const useGetProducts = () => {
    const url = process.env.REACT_APP_PROD_URL+'/api/products/query';
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);  
    async function fetchUrl() {
        const response = await fetch(url, 
            {
                mode: 'cors',
                method: 'GET',
                headers: {
                    "Accept":"application/json"
                }
            });
        const json = await response.json();
        setProducts(json);
        setLoading(false);
    }  
    useEffect(() => {
        fetchUrl();
    }, []);
    return [products, loading, setProducts];
}
export { useGetProducts };