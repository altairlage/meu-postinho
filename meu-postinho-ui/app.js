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

    // Lógica para a página de Confirmação (finalizar_visita.html)
    const finalizarForm = document.getElementById('finalizarForm');
    const mapContainer = document.getElementById('mapContainer');

    if (finalizarForm) {
        // Exibe a localização na página quando ela carrega
        if ("geolocation" in navigator) {
            navigator.geolocation.getCurrentPosition(
                // Callback de sucesso
                (posicao) => {
                    const latitude = posicao.coords.latitude;
                    const longitude = posicao.coords.longitude;
                    
                    // Atualiza o conteúdo do mapa
                    const mapIframe = `<iframe src="https://maps.google.com/maps?q=${latitude},${longitude}&output=embed" width="100%" height="300" style="border:0;" allowfullscreen="" loading="lazy"></iframe>`;
                    mapContainer.innerHTML = mapIframe;
                },
                // Callback de erro
                (error) => {
                    console.error("Erro ao obter a localização: ", error.message);
                    mapContainer.innerHTML = `<p class="text-danger text-center mt-4">Não foi possível obter sua localização. Por favor, ative a permissão de localização.</p>`;
                }
            );
        } else {
            mapContainer.innerHTML = `<p class="text-danger text-center mt-4">Seu navegador não suporta a Geolocation API.</p>`;
        }

        // Lógica para o envio dos dados
        finalizarForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            const visitaId = document.getElementById('visitaCheckin').value;
            
            // Verifica se o navegador suporta a Geolocation API
            if ("geolocation" in navigator) {
                navigator.geolocation.getCurrentPosition(async (posicao) => {
                    const latitude = posicao.coords.latitude;
                    const longitude = posicao.coords.longitude;

                    payload = {
                        latitude,
                        longitude
                    };

                    console.log("payload enviado:")
                    console.log(payload)

                    try {
                        const response = await fetch(`${host}/visits/finish/${visitaId}`, {
                            method: 'PUT',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify(payload),
                        });

                        if (response.ok) {
                            alert('Visita finalizada com sucesso!');
                            console.log('Resposta da API:', await response.json());
                        } else {
                            alert('Erro ao finalizar a visita. Verifique o console.');
                            console.error('Erro na requisição:', response.status, response.statusText);
                            console.error('Detalhes do erro:', await response.text());
                        }
                    } catch (error) {
                        alert('Erro de conexão com a API. Verifique se o seu servidor Spring Boot está rodando em http://localhost:8080.');
                        console.error('Erro na requisição fetch:', error);
                    }
                }, (error) => {
                    console.error("Erro ao obter a localização: ", error.message);
                    alert("Não foi possível obter sua localização. Por favor, verifique se a permissão de localização está ativada.");
                });
            } else {
                alert("Seu navegador não suporta a obtenção de localização.");
            }
        });
    }
});