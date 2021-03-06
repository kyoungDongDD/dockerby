import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LoginIcon from '@mui/icons-material/Login';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { Link, useHistory } from 'react-router-dom';
import axios from 'axios';

interface Message {
  message: string;
  status: string;
}

export default function Login() {
  const history = useHistory();

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const data = new FormData(event.currentTarget);
    const loginData = {
      principal: data.get('id'),
      credential: data.get('password'),
    };

    axios
      .post(`/api/user/auth/signin`, loginData, { withCredentials: true })
      .then((res) => {
        const datas = res.data as Message;
        if (datas.status === 'Success') {
          const date = new Date();
          sessionStorage.setItem('login', date.toString());
          history.push('/');
        } else {
          alert('로그인에 실패하였습니다.');
        }
      })
      .catch((error) => {
        alert(error);
      });
  };

  return (
    <Container component="main" maxWidth="xs">
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <img src="/assets/dockerby.png" width="360" height="360" alt="" />

        {/* <Box>
          <Typography component="h1" variant="h3" sx={{ color: '#35baf6' }}>
            Dockerby
          </Typography>
        </Box> */}
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            InputLabelProps={{ required: false }}
            required
            fullWidth
            id="id"
            label="아이디"
            name="id"
            autoComplete="id"
            autoFocus
          />
          <TextField
            margin="normal"
            InputLabelProps={{ required: false }}
            required
            fullWidth
            name="password"
            label="패스워드"
            type="password"
            id="password"
            autoComplete="current-password"
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{
              mt: 3,
              mb: 2,
              background: 'linear-gradient(45deg, #35baf6, #4dabf5)',
            }}
            startIcon={<LoginIcon />}
          >
            로그인
          </Button>
          <Grid container justifyContent="flex-end">
            {/* <Grid item xs>
                <Link href="#!" variant="body2">
                  Forgot password?
                </Link>
              </Grid> */}
            <Grid container direction="row-reverse">
              <Link to="/signup" style={{ textDecoration: 'none' }}>
                <Typography sx={{ color: 'black' }}>가입하기</Typography>
              </Link>
            </Grid>
          </Grid>
        </Box>
      </Box>
    </Container>
  );
}
