import React from 'react';
import '../App.css';

const Footer = () => {
  return (
    <footer className="footer">
        <div className="footer-content">
            <div className="footer-links">
                <a href="https://github.com/Shevial">GitHub</a>
                <a href="/terms">Términos de Uso</a>
                <a href="/contact">Contacto</a>
            </div>
        <p>© 2025 Medication Dosage App. Todos los derechos reservados.</p>
        </div>
    </footer>
  );
};

export default Footer;