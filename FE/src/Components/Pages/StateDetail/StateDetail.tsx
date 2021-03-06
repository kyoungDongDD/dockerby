import { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import CancelIcon from '@mui/icons-material/Cancel';
import PersonIcon from '@mui/icons-material/Person';
import { AiOutlineGitlab } from 'react-icons/ai';
import Typography from '@mui/material/Typography';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import { useParams, useHistory } from 'react-router-dom';
import axios from 'axios';

interface GitInfo {
  gitBranch: string;
  gitRepositoryUrl: string;
  username: string;
}

interface StateInfo {
  buildNumber: number;
  consoleLog: string;
  gitInfo: GitInfo;
  projectId: number;
  projectName: string;
  registDate: string;
  stateType: string;
}

export default function StateDetail() {
  const [stateData, setStateData] = useState<StateInfo>({
    buildNumber: 0,
    consoleLog: '',
    gitInfo: { gitBranch: '', gitRepositoryUrl: '', username: '' },
    projectId: 0,
    projectName: '',
    registDate: '',
    stateType: '',
  });
  // const location = useLocation();
  const params = useParams();
  const history = useHistory();

  const handleBackClick = () => {
    history.push(`/detail/${stateData.projectId}`);
  };

  useEffect(() => {
    axios.get(`/api/project/build/detail`, { params }).then((res) => {
      setStateData(res.data);
    });
  }, []);

  return (
    <Box mt={5}>
      <Box sx={{ width: '90%', padding: 3, marginX: 'auto' }}>
        <Stack spacing={2} direction="row" justifyContent="flex-end">
          <Button
            variant="contained"
            onClick={handleBackClick}
            sx={{ background: 'linear-gradient(195deg, #777, #191919)' }}
          >
            Back
          </Button>
        </Stack>
      </Box>
      <Grid mt={5} container direction="column" justifyContent="left">
        <Grid item display="flex">
          {stateData.stateType === 'Done' ? (
            <CheckCircleOutlineIcon
              color="success"
              sx={{ fontSize: 60, fontWeight: '300' }}
            />
          ) : (
            <CancelIcon color="error" sx={{ fontSize: 60 }} />
          )}
          <Typography variant="h2" sx={{ marginLeft: 4 }}>
            ???????????? : {stateData.projectName}
          </Typography>
        </Grid>
        <Grid item display="flex">
          <Typography mt={3} variant="h3" sx={{ marginLeft: 15 }}>
            ??????
            <Box
              sx={{
                display: 'inline',
                backgroundColor: 'rgb(33,33,33)',
                color: 'white',
                paddingX: 1,
                borderRadius: 5,
              }}
            >
              #{stateData.buildNumber}
            </Box>
            {stateData.registDate}
          </Typography>
        </Grid>

        {stateData.gitInfo ? (
          <>
            <Grid item display="flex" sx={{ m: 4 }}>
              <PersonIcon sx={{ fontSize: 35 }} />
              <Box sx={{ marginLeft: 3 }}>
                {stateData.gitInfo.username ? (
                  <Typography variant="h5">
                    {`????????? ${stateData.gitInfo.username}?????? ?????????????????????.`}
                  </Typography>
                ) : (
                  <Typography variant="h5">{' ????????? ?????? '}</Typography>
                )}
              </Box>
            </Grid>
            <Grid item display="flex" sx={{ my: 2, mx: 4 }}>
              <AiOutlineGitlab size={35} />
              <Box sx={{ marginLeft: 3 }}>
                <Typography>
                  ??? ????????? :
                  <a href="##" target="_blank">
                    {stateData.gitInfo.gitRepositoryUrl
                      ? `${stateData.gitInfo.gitRepositoryUrl}`
                      : '?????? ????????????.'}
                  </a>
                </Typography>
                <Typography>
                  ?????? ????????? :
                  {stateData.gitInfo.gitBranch
                    ? `${stateData.gitInfo.gitBranch}`
                    : '?????? ????????????.'}
                </Typography>
              </Box>
            </Grid>
          </>
        ) : null}
        <Box>
          <Typography variant="h5" sx={{ marginY: 3 }}>
            ?????? ??????
          </Typography>
          <Box
            sx={{
              width: 'max',
              padding: 3,
              whiteSpace: 'pre-wrap',
              backgroundColor: '#ddd',
              borderRadius: 3,
            }}
          >
            {stateData.consoleLog}
          </Box>
        </Box>
      </Grid>
    </Box>
  );
}
