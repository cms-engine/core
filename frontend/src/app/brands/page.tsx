"use client";

import { SetStateAction, useEffect, useState} from "react";

interface Brand {
  id: number;
  name: string;
}

export default function BrandsPage () {
  const [brands, setBrands] = useState([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(1);
  const [totalElements, setTotalElements] = useState(0);

  const pageSize = 2;

  const fetchBrands = async (page: number) => {
    setLoading(true);
    try {
      const response = await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL || ""}/admin/brands/search`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          page: page,
          size: pageSize,
          sorts: [], // Add any sorting options if needed
          filters: [], // Add any filters if needed
        }),
      });

      if (!response.ok) {
        throw new Error("Network response was not ok");
      }

      const data = await response.json();
      setBrands(data.data);
      setTotalElements(data.totalElements);
    } catch (error) {
      console.error("Error fetching brands:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBrands(page);
  }, [page]);

  const handlePageChange = (newPage: SetStateAction<number>) => {
    setPage(newPage);
  };

  const totalPages = Math.ceil(totalElements / pageSize);

  return (
    <div>
      <h1>Brands Management</h1>
      {loading ? (
        <p>Loading...</p>
      ) : (
        <div>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
              </tr>
            </thead>
            <tbody>
              {brands.map((brand: Brand) => (
                <tr key={brand.id}>
                  <td>{brand.id}</td>
                  <td>{brand.name}</td>
                </tr>
              ))}
            </tbody>
          </table>
          <div>
            <button onClick={() => handlePageChange(page - 1)} disabled={page <= 1}>
              Previous
            </button>
            <span>{` Page ${page} of ${totalPages} `}</span>
            <button onClick={() => handlePageChange(page + 1)} disabled={page >= totalPages}>
              Next
            </button>
          </div>
        </div>
      )}
    </div>
  );
};
