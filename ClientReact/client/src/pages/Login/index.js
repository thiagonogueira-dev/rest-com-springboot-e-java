import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './styles.css'

import api from '../../services/api';

import logoImage from '../../assets/logo.svg';
import padlock from '../../assets/padlock.png';

export default function Login() {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate();

    async function login(e) {
        e.preventDefault();

        const data = {
            username, 
            password
        };

        try {
            const response = await api.post('/auth/signin', data);

            localStorage.setItem('username', username);
            localStorage.setItem('accessToken', response.data.accessToken);

            navigate('/books');
        } catch(e) {
            alert('Erro ao fazer login, tente novamente');
        }
    };

    return (
        <div className="login-container">
            <section className="form">
                <img src={logoImage} alt='Erudio Logo'/>
                <form onSubmit={login}>
                    <h1>Acesse sua conta</h1>
                    <input
                         placeholder='Username'
                         value={username}
                         onChange={e => setUsername(e.target.value)}
                    />
                    <input
                         type='password' placeholder='Senha'
                         value={password}
                         onChange={e => setPassword(e.target.value)}
                    />
                    <button className='button' type='submit'>Login</button>
                </form>
            </section>

            <img src={padlock} alt='Login'/>
        </div>
    )
}