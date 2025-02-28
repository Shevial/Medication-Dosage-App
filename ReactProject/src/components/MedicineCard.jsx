import './MedicineCard.css'
import { useState } from 'react'

function MedicineCard(props) {
  const {medicine, selectMedicine}= props;
  if (!medicine) return null // Bloquea renderizado si no hay datos
  
  return (
    <li className="medicine-card" onClick={() => selectMedicine(medicine)}>
      <h2 className="medicine-name">{medicine.name || 'Unknown medication'}</h2>
      <p className="details">{medicine.details || 'No additional details'}</p>
    </li>
  )
}

export default MedicineCard