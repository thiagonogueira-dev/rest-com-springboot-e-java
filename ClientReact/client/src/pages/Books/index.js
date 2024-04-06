import React, { useState, useEffect } from "react";
import { Link,  } from 'react-router-dom';
import { FiPower, FiEdit, FiTrash2 } from 'react-icons/fi';

import api from '../../services/api';

import './styles.css';

import logoImage from '../../assets/logo.svg';

export default function Books() {

    const [books, setBooks] = useState([]);

    const username = localStorage.getItem('username');
    const accessToken = localStorage.getItem('accessToken');

    // const navigate = useNavigate();


    useEffect(() => {
        api.get('/api/book/v1', { 
            headers: {
                Authorization: `Bearer ${accessToken}`
            },
            params: {
                limit: 4
            }
        } 
        ).then(response => {
           setBooks(response.data._embedded.bookVOList);
        });
    });

    return (
        <div className="book-container">
            <header>
                <img src={logoImage} alt="Erudio"/>
                <span>Bem-vindo, <strong>{ username.toUpperCase() }</strong>!</span>
                <Link className="button" to="/book/new">Adicionar novo livro</Link>
                <button type="button">
                    <FiPower size={18} color="#251fc5" />
                </button>
            </header>

            <h1>Livros cadastrados</h1>
            <ul>
                {books.map(book =>
                    <li>
                        <strong>Título:</strong>
                        <p>{book.title}</p>
                        <strong>Autor:</strong>
                        <p>{book.author}</p>
                        <strong>Preço:</strong>
                        <p>{Intl.NumberFormat('pt-BR', {style: 'currency', currency: 'BRL'}).format(book.price)}</p>
                        <strong>Data de lançamento:</strong>
                        <p>{Intl.DateTimeFormat('pt-BR').format(new Date(book.launchDate))}</p>

                        <button type="button">
                            <FiEdit size={20} color="#251fc5"/>
                        </button>

                        <button type="button">
                            <FiTrash2 size={20} color="#251fc5"/>
                        </button>
                    </li>
                )}
            </ul>
        </div>
    )
}