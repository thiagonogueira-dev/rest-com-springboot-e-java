import React from 'react';
import { Link } from 'react-router-dom';
import { FiArrowLeft } from 'react-icons/fi';

import './styles.css';

import logoImage from '../../assets/logo.svg';

export default function NewBook() {
    return (
        <div className="new-book-container">
            <div className="content">
                <section className="form">
                    <img src={logoImage} alt="Erudio"/>
                    <h1>Adicionar um novo livro</h1>
                    <p>Escreva as informações do livro e clique em "Adicionar"!</p>
                    <Link className="back-link" to="/books">
                        <FiArrowLeft size={16} color="#251fc5"/>
                        Início
                    </Link>
                </section>
                <form>
                    <input placeholder="Título"/>
                    <input placeholder="Autor"/>
                    <input type="date"/>
                    <input placeholder="Preço"/>

                    <button className="button" type="submit">Adicionar</button>
                </form>
            </div>
        </div>
    );
}