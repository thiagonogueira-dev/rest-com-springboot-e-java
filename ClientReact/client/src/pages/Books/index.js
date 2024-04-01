import React from "react";
import { Link } from 'react-router-dom';
import { FiPower, FiEdit, FiTrash2 } from 'react-icons/fi';

import './styles.css';

import logoImage from '../../assets/logo.svg';

export default function Books() {
    return (
        <div className="book-container">
            <header>
                <img src={logoImage} alt="Erudio"/>
                <span>Bem-vindo, <strong>Usuário</strong>!</span>
                <Link className="button" to="/book/new">Adicionar novo livro</Link>
                <button type="button">
                    <FiPower size={18} color="#251fc5" />
                </button>
            </header>

            <h1>Livros cadastrados</h1>
            <ul>
                <li>
                    <strong>Título:</strong>
                    <p>Docker Deep Dive</p>
                    <strong>Autor:</strong>
                    <p>Irineu</p>
                    <strong>Preço:</strong>
                    <p>R$ 47,90</p>
                    <strong>Data de lançamento:</strong>
                    <p>12/07/2017</p>

                    <button type="button">
                        <FiEdit size={20} color="#251fc5"/>
                    </button>

                    <button type="button">
                        <FiTrash2 size={20} color="#251fc5"/>
                    </button>
                </li>
                <li>
                    <strong>Título:</strong>
                    <p>Docker Deep Dive</p>
                    <strong>Autor:</strong>
                    <p>Irineu</p>
                    <strong>Preço:</strong>
                    <p>R$ 47,90</p>
                    <strong>Data de lançamento:</strong>
                    <p>12/07/2017</p>

                    <button type="button">
                        <FiEdit size={20} color="#251fc5"/>
                    </button>

                    <button type="button">
                        <FiTrash2 size={20} color="#251fc5"/>
                    </button>
                </li>
                <li>
                    <strong>Título:</strong>
                    <p>Docker Deep Dive</p>
                    <strong>Autor:</strong>
                    <p>Irineu</p>
                    <strong>Preço:</strong>
                    <p>R$ 47,90</p>
                    <strong>Data de lançamento:</strong>
                    <p>12/07/2017</p>

                    <button type="button">
                        <FiEdit size={20} color="#251fc5"/>
                    </button>

                    <button type="button">
                        <FiTrash2 size={20} color="#251fc5"/>
                    </button>
                </li>
                <li>
                    <strong>Título:</strong>
                    <p>Docker Deep Dive</p>
                    <strong>Autor:</strong>
                    <p>Irineu</p>
                    <strong>Preço:</strong>
                    <p>R$ 47,90</p>
                    <strong>Data de lançamento:</strong>
                    <p>12/07/2017</p>

                    <button type="button">
                        <FiEdit size={20} color="#251fc5"/>
                    </button>

                    <button type="button">
                        <FiTrash2 size={20} color="#251fc5"/>
                    </button>
                </li>
                <li>
                    <strong>Título:</strong>
                    <p>Docker Deep Dive</p>
                    <strong>Autor:</strong>
                    <p>Irineu</p>
                    <strong>Preço:</strong>
                    <p>R$ 47,90</p>
                    <strong>Data de lançamento:</strong>
                    <p>12/07/2017</p>

                    <button type="button">
                        <FiEdit size={20} color="#251fc5"/>
                    </button>

                    <button type="button">
                        <FiTrash2 size={20} color="#251fc5"/>
                    </button>
                </li>
            </ul>
        </div>
    )
}