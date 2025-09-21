document.addEventListener('DOMContentLoaded', () => {

    const host = 'http://localhost:8080';

    // Lógica para a página de Login (index.html)
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const username = document.getElementById('username').value;
            // Salva o nome de usuário na sessão para usar na página home
            sessionStorage.setItem('username', username);
            window.location.href = 'home.html';
        });
    }

    // Lógica para a página Inicial (home.html)
    const welcomeMessage = document.getElementById('welcomeMessage');
    if (welcomeMessage) {
        const username = sessionStorage.getItem('username') || 'Usuário';
        welcomeMessage.textContent = `Olá, ${username}`;
    }

    // Lógica para a página de Agendamento (agendar_visita.html)
    const agendarForm = document.getElementById('agendarForm');
    if (agendarForm) {
        agendarForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const agente = 1;
            const paciente = document.getElementById('paciente').value;
            const dataHora = document.getElementById('dataHora').value;
            const motivo = document.getElementById('motivo').value;
            const observacoes = document.getElementById('observacoes').value;
            const status = "PENDENTE";
            
            payload = {
                "healthAgentId": agente,
                "patientId": paciente,
                // "visitDate": "2025-10-20T10:00:00",
                "visitDate": dataHora,
                "reason": motivo,
                "observations": observacoes,
                "status": status
            }

            // const payload = {
            //     agente,
            //     paciente,
            //     dataHora,
            //     motivo,
            //     observacoes,
            //     status
            // };

            try {
                const response = await fetch(`${host}/visits/create`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(payload),
                });

                if (response.ok) {
                    alert('Visita agendada com sucesso!');
                    console.log('Resposta da API:', await response.json());
                } else {
                    alert('Erro ao agendar a visita. Verifique o console.');
                    console.error('Erro na requisição:', response.status, response.statusText);
                    console.error('Detalhes do erro:', await response.text());
                }
            } catch (error) {
                alert('Erro de conexão com a API. Verifique se o seu servidor Spring Boot está rodando em http://localhost:8080.');
                console.error('Erro na requisição fetch:', error);
            }
        });
    }

    // Lógica para a página de Confirmação (confirmar_visita.html)
    const confirmarForm = document.getElementById('confirmarForm');
    if (confirmarForm) {
        confirmarForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            const paciente = document.getElementById('visitaCheckin').value;
            const localizacaoSimulada = {
                latitude: -22.913399,
                longitude: -43.209395
            };

            const payload = {
                paciente,
                localizacao: localizacaoSimulada
            };

            try {
                const response = await fetch(`${host}/checkin`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(payload),
                });

                if (response.ok) {
                    alert('Visita confirmada com sucesso!');
                    console.log('Resposta da API:', await response.json());
                } else {
                    alert('Erro ao confirmar a visita. Verifique o console.');
                    console.error('Erro na requisição:', response.status, response.statusText);
                    console.error('Detalhes do erro:', await response.text());
                }
            } catch (error) {
                alert('Erro de conexão com a API. Verifique se o seu servidor Spring Boot está rodando em http://localhost:8080.');
                console.error('Erro na requisição fetch:', error);
            }
        });
    }
});