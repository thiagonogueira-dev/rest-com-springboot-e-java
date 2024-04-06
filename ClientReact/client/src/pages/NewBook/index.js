import React, { useState } from 'react';
import { Link, useNavigate  } from 'react-router-dom';
import { FiArrowLeft } from 'react-icons/fi';

import api from '../../services/api';

import './styles.css';

import logoImage from '../../assets/logo.svg';

export default function NewBook() {

    
    // const [id, setId] = useState(null);
    const [author, setAuthor] = useState('');
    const [launchDate, setLaunchDate] = useState('');
    const [price, setPrice] = useState('');
    const [title, setTitle] = useState('');

    // const username = localStorage.getItem('username');
    const accessToken = localStorage.getItem('accessToken');

    const navigate = useNavigate();

    async function createNewBook(e) {
        e.preventDefault();

        const data = {
            title,
            author,
            launchDate,
            price
        }

        const headers = {
            Authorization: `Bearer ${accessToken}`
        }

        try {
            await api.post('/api/book/v1', data, { headers });
            navigate('/books');
        } catch(err) {
            alert('Erro ao salvar livro! Tente novamente!');
        }
    }

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
                <form onSubmit={ createNewBook }>
                    <input
                        placeholder="Título"
                        value={title}
                        onChange={ e => setTitle(e.target.value)}
                    />
                    <input
                        placeholder="Autor"
                        value={author}
                        onChange={ e => setAuthor(e.target.value)}
                    />
                    <input
                        type="date"
                        value={launchDate}
                        onChange={ e => setLaunchDate(e.target.value)}
                    />
                    <input
                        placeholder="Preço"
                        value={price}
                        onChange={ e => setPrice(e.target.value)}
                    />

                    <button className="button" type="submit">Adicionar</button>
                </form>
            </div>
        </div>
    );
}